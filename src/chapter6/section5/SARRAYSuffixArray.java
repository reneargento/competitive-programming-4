package chapter6.section5;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 16/09/2026.
 */
public class SARRAYSuffixArray {

    private static final int MAX_TEXT = 100_000;
    private static final int MAXN = MAX_TEXT + 12;
    private static final int MAX_SYMBOL = 256;
    private static final int MAIN_BUCKET = MAX_SYMBOL + 2;
    private static final int MAX_BUCKET = MAXN;
    private static final int MAX_DEPTH = 32;
    private static final byte[] TEXT = new byte[MAX_TEXT];
    private static final int[] S = new int[MAXN];
    private static final int[] SA = new int[MAXN];
    private static final int[] SUM_L = new int[MAX_BUCKET];
    private static final int[] SUM_S = new int[MAX_BUCKET];
    /*
     * SUM_L is modified by induce().
     * This holds the original bucket boundaries for the current level.
     */
    private static final int[] SUM_L_ORIGINAL = new int[MAX_BUCKET];
    /*
     * Reused by induce():
     *   - LMS bucket heads
     *   - original L bucket ends for S-induction
     */
    private static final int[] INDUCE_BUF = new int[MAX_BUCKET];
    private static final boolean[] LS = new boolean[MAXN];
    private static final int[] LMS = new int[MAXN];
    private static final int[] LMS_MAP = new int[MAXN];
    private static final int[] SORTED_LMS = new int[MAXN];
    private static final int[] REC_S = new int[MAXN];
    private static final int[] REC_SA = new int[MAXN];
    private static final int[] RANK = new int[MAXN];
    private static final int[] RANK_TMP = new int[MAXN];
    private static final int[] LMS_SAVE = new int[MAXN];
    private static final int[] REDUCE_LMS_STACK = new int[MAXN];
    private static int reduceLmsSp;
    /*
     * Parent state.
     */
    private static final boolean[][] LS_SAVE = new boolean[MAX_DEPTH][];
    private static final int[][] SUM_S_SAVE = new int[MAX_DEPTH][];
    private static final int[][] SUM_L_SAVE = new int[MAX_DEPTH][];

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int textLength = readText();
        for (int i = 0; i < textLength; i++) {
            S[i] = (TEXT[i] & 0xff) + 1;
        }
        S[textLength] = 0;
        saIs(S, SA, textLength + 1, MAX_SYMBOL, MAIN_BUCKET, 0);

        for (int i = 1; i <= textLength; i++) {
            outputWriter.printLine(SA[i]);
        }
        outputWriter.flush();
    }

    private static int readText() throws IOException {
        int length = 0;
        int c;

        while ((c = FastReader.read()) != -1) {
            if (c > ' ') {
                TEXT[length++] = (byte) c;
                break;
            }
        }
        while ((c = FastReader.read()) != -1 &&
                c > ' ') {
            TEXT[length++] = (byte) c;
        }
        return length;
    }

    // =====================================================================
    // SA-IS
    // =====================================================================

    private static void saIs(int[] s, int[] sa, int n, int maxSymbol, int bucketSize, int depth) {
        if (n <= 1) {
            if (n == 1) {
                sa[0] = 0;
            }
            return;
        }
        if (n < 10) {
            naiveSa(s, sa, n);
            return;
        }
        if (n < 40) {
            doublingSa(s, sa, n);
            return;
        }

        computeLsAndBuckets(s, n, maxSymbol, bucketSize);
        int m = fillLms(n);

        /*
         * Save original bucket ends.
         */
        System.arraycopy(SUM_L, 0, SUM_L_ORIGINAL, 0, bucketSize);
        induce(s, sa, n, bucketSize, m, LMS);

        if (m == 0) {
            return;
        }

        for (int i = 0; i < m; i++) {
            LMS_MAP[LMS[i]] = i + 1;
        }

        int p = 0;
        for (int i = 0; i < n; i++) {
            int v = sa[i];
            if (v >= 0 &&
                    LMS_MAP[v] != 0) {
                SORTED_LMS[p++] = v;
            }
        }

        int recUpper = nameReducedInto(s, n, m, REC_S);

        for (int i = 0; i < m; i++) {
            LMS_MAP[LMS[i]] = 0;
        }
        System.arraycopy(LMS, 0, LMS_SAVE, 0, m);

        /*
         * All LMS substrings have unique names.
         */
        if (recUpper + 1 == m) {
            for (int i = 0; i < m; i++) {
                REC_SA[REC_S[i]] = i;
            }
            for (int i = 0; i < m; i++) {
                SORTED_LMS[REC_S[i]] = LMS_SAVE[i];
            }
            System.arraycopy(SUM_L_ORIGINAL, 0, SUM_L, 0, bucketSize);
            induce(s, sa, n, bucketSize, m, SORTED_LMS);
            return;
        }

        System.arraycopy(SUM_L_ORIGINAL, 0, SUM_L, 0, bucketSize);
        saveParentState(depth, n, bucketSize);
        refSaIsStatic(REC_S, REC_SA, m, recUpper, depth + 1);

        for (int i = 0; i < m; i++) {
            if (REC_SA[i] < 0) {
                refSaIsAlloc(REC_S, REC_SA, m, recUpper);
                break;
            }
        }
        for (int i = 0; i < m; i++) {
            SORTED_LMS[i] = LMS_SAVE[REC_SA[i]];
        }
        restoreParentState(depth, n, bucketSize);
        induce(s, sa, n, bucketSize, m, SORTED_LMS);
    }

    // =====================================================================
    // Recursive SA-IS
    // =====================================================================

    private static void refSaIsStatic(int[] s, int[] sa, int n, int upper, int depth) {
        if (n <= 1) {
            if (n == 1) {
                sa[0] = 0;
            }
            return;
        }
        if (n < 10) {
            naiveSa(s, sa, n);
            return;
        }
        if (n < 40) {
            doublingSa(s, sa, n);
            return;
        }

        int bucketSize = upper + 2;
        computeLsAndBuckets(s, n, upper, bucketSize);
        int m = fillLms(n);

        System.arraycopy(SUM_L, 0, SUM_L_ORIGINAL, 0, bucketSize);
        induce(s, sa, n, bucketSize, m, LMS);

        if (m == 0) {
            return;
        }
        for (int i = 0; i < m; i++) {
            LMS_MAP[LMS[i]] = i + 1;
        }

        int p = 0;
        for (int i = 0; i < n; i++) {
            int v = sa[i];
            if (v >= 0 &&
                    LMS_MAP[v] != 0) {
                SORTED_LMS[p++] = v;
            }
        }

        int[] names =
                s == RANK_TMP
                        ? RANK
                        : RANK_TMP;
        int recUpper = nameReducedInto(s, n, m, names);

        for (int i = 0; i < m; i++) {
            LMS_MAP[LMS[i]] = 0;
        }
        int holdBase = reduceLmsSp;
        System.arraycopy(LMS, 0, REDUCE_LMS_STACK, reduceLmsSp, m);
        reduceLmsSp += m;

        /*
         * Unique names.
         */
        if (recUpper + 1 == m) {
            for (int i = 0; i < m; i++) {
                sa[names[i]] = i;
            }
            for (int i = 0; i < m; i++) {
                SORTED_LMS[names[i]] = LMS[i];
            }

            System.arraycopy(SUM_L_ORIGINAL, 0, SUM_L, 0, bucketSize);
            induce(s, sa, n, bucketSize, m, SORTED_LMS);
            reduceLmsSp = holdBase;
            return;
        }

        /*
         * Restore SUM_L before saving parent state.
         */
        System.arraycopy(SUM_L_ORIGINAL, 0, SUM_L, 0, bucketSize);
        saveParentState(depth, n, bucketSize);

        int[] childS =
                s == REC_S
                        ? RANK
                        : REC_S;
        System.arraycopy(names, 0, childS, 0, m);
        refSaIsStatic(childS, REC_SA, m, recUpper, depth + 1);

        for (int i = 0; i < m; i++) {
            SORTED_LMS[i] = REDUCE_LMS_STACK[holdBase + REC_SA[i]];
        }
        reduceLmsSp = holdBase;
        restoreParentState(depth, n, bucketSize);
        induce(s, sa, n, bucketSize, m, SORTED_LMS);
    }

    // =====================================================================
    // Parent state
    // =====================================================================

    private static void saveParentState(int depth, int n, int bucketSize) {
        if (depth >= MAX_DEPTH) {
            throw new RuntimeException("SA-IS recursion depth");
        }
        boolean[] lsSave = LS_SAVE[depth];

        if (lsSave == null ||
                lsSave.length < n) {
            lsSave = new boolean[n];
            LS_SAVE[depth] = lsSave;
        }

        int[] sumSSave = SUM_S_SAVE[depth];
        if (sumSSave == null ||
                sumSSave.length < bucketSize) {
            sumSSave = new int[bucketSize];
            SUM_S_SAVE[depth] = sumSSave;
        }

        int[] sumLSave = SUM_L_SAVE[depth];
        if (sumLSave == null ||
                sumLSave.length < bucketSize) {
            sumLSave = new int[bucketSize];
            SUM_L_SAVE[depth] = sumLSave;
        }
        System.arraycopy(LS, 0, lsSave, 0, n);
        System.arraycopy(SUM_S, 0, sumSSave, 0, bucketSize);
        System.arraycopy(SUM_L, 0, sumLSave, 0, bucketSize);
    }

    private static void restoreParentState(int depth, int n, int bucketSize) {
        System.arraycopy(LS_SAVE[depth], 0, LS, 0, n);
        System.arraycopy(SUM_S_SAVE[depth], 0, SUM_S, 0, bucketSize);
        System.arraycopy(SUM_L_SAVE[depth], 0, SUM_L, 0, bucketSize);
    }

    // =====================================================================
    // Classification and buckets
    // =====================================================================

    private static void computeLsAndBuckets(int[] s, int n, int maxSymbol, int bucketSize) {
        Arrays.fill(SUM_L, 0, bucketSize, 0);
        Arrays.fill(SUM_S, 0, bucketSize, 0);

        LS[n - 1] = false;
        SUM_S[s[n - 1]]++;

        for (int i = n - 2; i >= 0; i--) {
            int a = s[i];
            int b = s[i + 1];
            boolean type = a == b
                            ? LS[i + 1]
                            : a < b;
            LS[i] = type;

            if (type) {
                SUM_L[a + 1]++;
            } else {
                SUM_S[a]++;
            }
        }

        for (int i = 0; i <= maxSymbol && i < bucketSize; i++) {
            SUM_S[i] += SUM_L[i];
            if (i < maxSymbol && i + 1 < bucketSize) {
                SUM_L[i + 1] += SUM_S[i];
            }
        }
    }

    private static int fillLms(int n) {
        int m = 0;
        for (int i = 1; i < n; i++) {
            if (!LS[i - 1] && LS[i]) {
                LMS[m++] = i;
            }
        }
        return m;
    }

    // =====================================================================
    // Optimized induce
    // =====================================================================

    private static void induce(int[] s, int[] sa, int n, int bucketSize, int m, int[] lms) {
        Arrays.fill(sa, 0, n, -1);
        System.arraycopy(SUM_S, 0, INDUCE_BUF, 0, bucketSize);

        for (int i = 0; i < m; i++) {
            int d = lms[i];
            if (d != n) {
                sa[INDUCE_BUF[s[d]]++] = d;
            }
        }

        /*
         * Preserve original L bucket ends in INDUCE_BUF.
         *
         * SUM_L becomes the mutable L pointer.
         */
        System.arraycopy(SUM_L, 0, INDUCE_BUF, 0, bucketSize);
        sa[SUM_L[s[n - 1]]++] = n - 1;

        /*
         * L-induction.
         */
        for (int i = 0; i < n; i++) {
            int v = sa[i];
            if (v >= 1) {
                int prev = v - 1;
                if (!LS[prev]) {
                    sa[SUM_L[s[prev]]++] = prev;
                }
            }
        }

        /*
         * S-induction.
         */
        for (int i = n - 1; i >= 0; i--) {
            int v = sa[i];
            if (v >= 1) {
                int prev = v - 1;
                if (LS[prev]) {
                    sa[--INDUCE_BUF[s[prev] + 1]] = prev;
                }
            }
        }
    }

    // =====================================================================
    // LMS naming
    // =====================================================================

    private static int nameReducedInto(int[] s, int n, int m, int[] outRecS) {
        int recUpper = 0;
        int first = LMS_MAP[SORTED_LMS[0]] - 1;
        outRecS[first] = 0;

        for (int i = 1; i < m; i++) {
            int left = SORTED_LMS[i - 1];
            int right = SORTED_LMS[i];
            int leftIndex = LMS_MAP[left] - 1;
            int rightIndex = LMS_MAP[right] - 1;
            int leftEnd =
                    leftIndex + 1 < m
                            ? LMS[leftIndex + 1]
                            : n;
            int rightEnd =
                    rightIndex + 1 < m
                            ? LMS[rightIndex + 1]
                            : n;
            boolean same = leftEnd - left == rightEnd - right;

            if (same) {
                int a = left;
                int b = right;

                while (a < leftEnd) {
                    if (s[a] != s[b]) {
                        same = false;
                        break;
                    }
                    a++;
                    b++;
                }
            }

            if (!same) {
                recUpper++;
            }
            outRecS[rightIndex] = recUpper;
        }
        return recUpper;
    }

    // =====================================================================
    // Allocation fallback
    // =====================================================================

    private static void refSaIsAlloc(int[] s, int[] sa, int n, int upper) {
        if (n <= 1) {
            if (n == 1) {
                sa[0] = 0;
            }
            return;
        }
        boolean[] ls = new boolean[n];
        ls[n - 1] = false;

        for (int i = n - 2; i >= 0; i--) {
            int a = s[i];
            int b = s[i + 1];
            ls[i] =
                    a == b
                            ? ls[i + 1]
                            : a < b;
        }

        int[] sumL = new int[upper + 2];
        int[] sumS = new int[upper + 2];

        for (int i = 0; i < n; i++) {
            if (ls[i]) {
                sumL[s[i] + 1]++;
            } else {
                sumS[s[i]]++;
            }
        }

        for (int i = 0; i <= upper; i++) {
            sumS[i] += sumL[i];
            if (i < upper) {
                sumL[i + 1] += sumS[i];
            }
        }

        int m = 0;
        for (int i = 1; i < n; i++) {
            if (!ls[i - 1] && ls[i]) {
                m++;
            }
        }

        int[] lms = new int[m];
        int p = 0;
        for (int i = 1; i < n; i++) {
            if (!ls[i - 1] && ls[i]) {
                lms[p++] = i;
            }
        }

        induceAlloc(s, sa, n, ls, sumL, sumS, lms);

        if (m == 0) {
            return;
        }

        int[] lmsMap = new int[n];
        Arrays.fill(lmsMap, -1);
        for (int i = 0; i < m; i++) {
            lmsMap[lms[i]] = i;
        }

        int[] sortedLms = new int[m];
        p = 0;
        for (int i = 0; i < n; i++) {
            int v = sa[i];
            if (v >= 0 &&
                    lmsMap[v] >= 0) {
                sortedLms[p++] = v;
            }
        }

        int[] recS = new int[m];
        int recUpper = 0;
        recS[lmsMap[sortedLms[0]]] = 0;

        for (int i = 1; i < m; i++) {
            int left = sortedLms[i - 1];
            int right = sortedLms[i];
            int li = lmsMap[left];
            int ri = lmsMap[right];

            int leftEnd =
                    li + 1 < m
                            ? lms[li + 1]
                            : n;
            int rightEnd =
                    ri + 1 < m
                            ? lms[ri + 1]
                            : n;
            boolean same = leftEnd - left == rightEnd - right;

            if (same) {
                int a = left;
                int b = right;

                while (a < leftEnd) {
                    if (s[a] != s[b]) {
                        same = false;
                        break;
                    }
                    a++;
                    b++;
                }
            }
            if (!same) {
                recUpper++;
            }
            recS[ri] = recUpper;
        }

        int[] recSa = new int[m];
        if (recUpper + 1 == m) {
            for (int i = 0; i < m; i++) {
                recSa[recS[i]] = i;
            }
        } else {
            refSaIsAlloc(recS, recSa, m, recUpper);
        }

        for (int i = 0; i < m; i++) {
            sortedLms[i] = lms[recSa[i]];
        }
        induceAlloc(s, sa, n, ls, sumL, sumS, sortedLms);
    }

    private static void induceAlloc(int[] s, int[] sa, int n, boolean[] ls, int[] sumL, int[] sumS, int[] lms) {
        Arrays.fill(sa, 0, n, -1);
        int[] buf = new int[sumS.length];
        System.arraycopy(sumS, 0, buf, 0, buf.length);

        for (int d : lms) {
            if (d != n) {
                sa[buf[s[d]]++] = d;
            }
        }
        System.arraycopy(sumL, 0, buf, 0, buf.length);
        sa[sumL[s[n - 1]]++] = n - 1;

        for (int i = 0; i < n; i++) {
            int v = sa[i];
            if (v >= 1) {
                int prev = v - 1;
                if (!ls[prev]) {
                    sa[sumL[s[prev]]++] = prev;
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int v = sa[i];
            if (v >= 1) {
                int prev = v - 1;
                if (ls[prev]) {
                    sa[--buf[s[prev] + 1]] = prev;
                }
            }
        }
    }

    // =====================================================================
    // Small SA
    // =====================================================================

    private static void naiveSa(int[] s, int[] sa, int n) {
        for (int i = 0; i < n; i++) {
            sa[i] = i;
        }

        for (int i = 1; i < n; i++) {
            int v = sa[i];
            int j = i - 1;

            while (j >= 0 &&
                    less(s, v, sa[j], n)) {
                sa[j + 1] = sa[j];
                j--;
            }
            sa[j + 1] = v;
        }
    }

    private static boolean less(int[] s, int a, int b, int n) {
        while (a < n && b < n) {
            if (s[a] != s[b]) {
                return s[a] < s[b];
            }
            a++;
            b++;
        }
        return a == n;
    }

    private static void doublingSa(int[] s, int[] sa, int n) {
        for (int i = 0; i < n; i++) {
            sa[i] = i;
            RANK[i] = s[i];
        }

        for (int k = 1; k < n; k <<= 1) {
            for (int i = 1; i < n; i++) {
                int v = sa[i];
                int j = i - 1;

                while (j >= 0 &&
                        compareRank(RANK, v, sa[j], k, n) < 0) {
                    sa[j + 1] = sa[j];
                    j--;
                }
                sa[j + 1] = v;
            }

            RANK_TMP[sa[0]] = 0;
            int classes = 0;
            for (int i = 1; i < n; i++) {
                if (compareRank(RANK, sa[i - 1], sa[i], k, n) != 0) {
                    classes++;
                }
                RANK_TMP[sa[i]] = classes;
            }

            System.arraycopy(RANK_TMP, 0, RANK, 0, n);
            if (classes == n - 1) {
                break;
            }
        }
    }

    private static int compareRank(int[] rank, int a, int b, int k, int n) {
        if (rank[a] != rank[b]) {
            return rank[a] < rank[b]
                    ? -1
                    : 1;
        }
        int ra =
                a + k < n
                        ? rank[a + k]
                        : 0;
        int rb =
                b + k < n
                        ? rank[b + k]
                        : 0;
        if (ra == rb) {
            return 0;
        }
        return ra < rb ? -1 : 1;
    }

    // =====================================================================
    // Fast input
    // =====================================================================

    private static class FastReader {
        private static final InputStream IN = System.in;
        private static final byte[] BUFFER = new byte[8192];
        private static int bufferPointer;
        private static int bytesRead;

        static void init() {
            bufferPointer = 0;
            bytesRead = 0;
        }

        static int read() throws IOException {
            if (bufferPointer >= bytesRead) {
                bytesRead = IN.read(BUFFER);
                bufferPointer = 0;
                if (bytesRead == -1) {
                    return -1;
                }
            }
            return BUFFER[bufferPointer++] & 0xff;
        }
    }

    // =====================================================================
    // Fast output
    // =====================================================================

    private static class OutputWriter {
        private final OutputStream out;
        private final byte[] buffer = new byte[1 << 16];
        private int bufferPointer;

        OutputWriter(OutputStream out) {
            this.out = out;
        }

        void printLine(int value) throws IOException {
            if (value == 0) {
                buffer[bufferPointer++] = '0';
                buffer[bufferPointer++] = '\n';
                flushBufferIfNeeded();
                return;
            }

            int start = bufferPointer;
            while (value > 0) {
                buffer[bufferPointer++] = (byte) ('0' + value % 10);
                value /= 10;
            }

            int end = bufferPointer - 1;
            while (start < end) {
                byte temp = buffer[start];
                buffer[start] = buffer[end];
                buffer[end] = temp;
                start++;
                end--;
            }
            buffer[bufferPointer++] = '\n';
            flushBufferIfNeeded();
        }

        private void flushBufferIfNeeded() throws IOException {
            if (bufferPointer >= buffer.length - 32) {
                out.write(buffer, 0, bufferPointer);
                bufferPointer = 0;
            }
        }

        void flush() throws IOException {
            if (bufferPointer > 0) {
                out.write(buffer, 0, bufferPointer);
                bufferPointer = 0;
            }
            out.flush();
        }
    }
}