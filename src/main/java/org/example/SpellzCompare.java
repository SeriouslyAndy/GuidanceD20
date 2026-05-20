package org.example;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * ==========================================================================
 *  NAT_2_0  -  The Big Nat 20 Spell Comparator
 * ==========================================================================
 *
 *  Scores how COMPLEMENTARY two spells are on a 20-point scale.
 *  Higher = better. A perfect 20 means the two spells do completely
 *  different things and pair very well together. A low score means
 *  they overlap heavily and the player should probably drop one of
 *  them for variety.
 *
 *  The 20 points come from two independent 10-point halves:
 *
 *      TOTAL = MT (Main Tags / hexagon)  +  T (Tags)
 *              ---- max 10 ----            -- max 10 --
 *
 *  ------------------------------------------------------------------
 *  MT - the hexagon area (Damage, Mitigation, Utility,
 *                         Roleplay, Healing, Unity)
 *  ------------------------------------------------------------------
 *  The 6 stats are plotted on a hexagon with 60-degree sectors.
 *  Each sector is a triangle whose two sides are consecutive stat
 *  values and whose included angle is 60 degrees, so:
 *
 *      sector_area = 1/2 * v_i * v_{i+1} * sin(60 deg)
 *
 *  Sum over the 6 sectors gives the spell's full hexagon area.
 *
 *  For two spells, in each sector the geometric intersection is
 *  bounded by the SMALLER stat at each endpoint:
 *
 *      overlap_area_i = 1/2 * min(A_i, B_i) * min(A_{i+1}, B_{i+1}) * sin(60)
 *
 *  We then collapse to a single similarity number using Intersection
 *  over Union (a.k.a. Jaccard for areas):
 *
 *      IoU = overlap / (area_A + area_B - overlap)
 *
 *  And invert it so that LOW overlap = HIGH score:
 *
 *      MT_score = 10 * (1 - IoU)
 *
 *  ------------------------------------------------------------------
 *  T - flavour tags (admin-defined, unlimited)
 *  ------------------------------------------------------------------
 *  Each spell has a set of textual tags like "concentration",
 *  "area-damage", "long-range", "fire", "ranged", "control"...
 *  Same idea, this time on a Jaccard over the tag SETS:
 *
 *      sim = |A intersect B| / |A union B|
 *      T_score = 10 * (1 - sim)
 *
 *  If one or both spells have no tags yet we return a neutral
 *  5/10 - we don't punish or reward a spell for missing data.
 *  Replace this default once the admin tagging UI exists.
 * ==========================================================================
 */
@Service
public class SpellzCompare {

    // ---- constants ---------------------------------------------------------

    /** sin(60 deg) = sqrt(3) / 2  ~= 0.8660254 */
    private static final double SIN_60 = Math.sqrt(3.0) / 2.0;

    /** Pre-factored 1/2 * sin(60 deg) so each sector is just v_i * v_{i+1}. */
    private static final double SECTOR_K = SIN_60 / 2.0;

    public static final double MAX_T_SCORE  = 10.0;
    public static final double MAX_MT_SCORE = 10.0;
    public static final double MAX_TOTAL    = MAX_T_SCORE + MAX_MT_SCORE;  // 20

    // ---- public API --------------------------------------------------------

    /**
     * Full NAT_2_0 comparison between two spells.
     * Order of a / b does not matter - the result is symmetric.
     */
    public Nat20Result compare(Spell a, Spell b) {
        double areaA   = hexArea(a);
        double areaB   = hexArea(b);
        double overlap = hexOverlapArea(a, b);

        double mtScore = mainTagScore(areaA, areaB, overlap);
        double tScore  = tagScore(a, b);

        return new Nat20Result(
                round1(mtScore + tScore),
                round1(mtScore),
                round1(tScore),
                round1(areaA),
                round1(areaB),
                round1(overlap)
        );
    }

    // ---- MT (hexagon) ------------------------------------------------------

    /** Total area of a single spell's hexagon. */
    public double hexArea(Spell s) {
        int[] v = hexValues(s);
        double sum = 0.0;
        for (int i = 0; i < 6; i++) {
            int next = (i + 1) % 6;
            sum += v[i] * v[next];
        }
        return SECTOR_K * sum;
    }

    /** Area of the geometric intersection of two spells' hexagons. */
    public double hexOverlapArea(Spell a, Spell b) {
        int[] va = hexValues(a);
        int[] vb = hexValues(b);
        double sum = 0.0;
        for (int i = 0; i < 6; i++) {
            int next = (i + 1) % 6;
            double minStart = Math.min(va[i],    vb[i]);
            double minEnd   = Math.min(va[next], vb[next]);
            sum += minStart * minEnd;
        }
        return SECTOR_K * sum;
    }

    /** MT score (0..10). Less geometric overlap = higher score. */
    private double mainTagScore(double areaA, double areaB, double overlap) {
        double union = areaA + areaB - overlap;
        if (union <= 0.0) {
            // Both hexagons are empty - treat as identical / no info.
            return 0.0;
        }
        double iou = overlap / union;
        return MAX_MT_SCORE * (1.0 - iou);
    }

    /**
     * Order matches the radar chart labels in spell-helper.html:
     * Damage, Mitigation, Utility, Roleplay, Healing, Unity.
     */
    private int[] hexValues(Spell s) {
        return new int[] {
                s.getScoreDamage(),
                s.getScoreMitigation(),
                s.getScoreUtility(),
                s.getScoreRoleplay(),
                s.getScoreHealing(),
                s.getScoreUnity()
        };
    }

    // ---- T (tags) ----------------------------------------------------------

    /**
     * T score (0..10) via Jaccard similarity over the tag sets.
     * Missing tag data on either side -> neutral 5/10.
     */
    public double tagScore(Spell a, Spell b) {
        Set<String> tagsA = normalisedTags(a);
        Set<String> tagsB = normalisedTags(b);

        // If either side has no tags we can't make a meaningful judgement.
        if (tagsA.isEmpty() || tagsB.isEmpty()) {
            return MAX_T_SCORE / 2.0;
        }

        Set<String> intersection = new HashSet<>(tagsA);
        intersection.retainAll(tagsB);

        Set<String> union = new HashSet<>(tagsA);
        union.addAll(tagsB);

        double similarity = (double) intersection.size() / union.size();
        return MAX_T_SCORE * (1.0 - similarity);
    }

    private Set<String> normalisedTags(Spell s) {
        if (s.getTags() == null || s.getTags().isEmpty()) {
            return Collections.emptySet();
        }
        Set<String> set = new HashSet<>();
        for (String t : s.getTags()) {
            if (t == null) continue;
            String trimmed = t.trim().toLowerCase();
            if (!trimmed.isEmpty()) set.add(trimmed);
        }
        return set;
    }

    // ---- helpers -----------------------------------------------------------

    private double round1(double v) {
        return Math.round(v * 10.0) / 10.0;
    }

    // ---- result DTO --------------------------------------------------------

    /**
     * Plain DTO so Spring serialises it straight to JSON for the UI.
     * All values are rounded to one decimal place.
     */
    public static class Nat20Result {
        private final double totalScore;     // 0..20
        private final double mainTagScore;   // 0..10  (the hexagon)
        private final double tagScore;       // 0..10  (the flavour tags)
        private final double hexAreaA;
        private final double hexAreaB;
        private final double overlapArea;

        public Nat20Result(double totalScore, double mainTagScore,
                           double tagScore, double hexAreaA,
                           double hexAreaB, double overlapArea) {
            this.totalScore   = totalScore;
            this.mainTagScore = mainTagScore;
            this.tagScore     = tagScore;
            this.hexAreaA     = hexAreaA;
            this.hexAreaB     = hexAreaB;
            this.overlapArea  = overlapArea;
        }

        public double getTotalScore()   { return totalScore; }
        public double getMainTagScore() { return mainTagScore; }
        public double getTagScore()     { return tagScore; }
        public double getHexAreaA()     { return hexAreaA; }
        public double getHexAreaB()     { return hexAreaB; }
        public double getOverlapArea()  { return overlapArea; }
    }
}
