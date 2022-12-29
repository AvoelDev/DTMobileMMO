package com.android.easy2pay;

/* loaded from: classes3.dex */
class XMLParseException extends RuntimeException {
    public static final int NO_LINE = -1;
    private int lineNr;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public XMLParseException(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "XML Parse Exception during parsing of "
            r0.<init>(r1)
            if (r4 != 0) goto Lc
            java.lang.String r4 = "the XML definition"
            goto L1f
        Lc:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "a "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r4 = " element"
            r1.append(r4)
            java.lang.String r4 = r1.toString()
        L1f:
            r0.append(r4)
            java.lang.String r4 = ": "
            r0.append(r4)
            r0.append(r5)
            java.lang.String r4 = r0.toString()
            r3.<init>(r4)
            r4 = -1
            r3.lineNr = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.easy2pay.XMLParseException.<init>(java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public XMLParseException(java.lang.String r4, int r5, java.lang.String r6) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "XML Parse Exception during parsing of "
            r0.<init>(r1)
            if (r4 != 0) goto Lc
            java.lang.String r4 = "the XML definition"
            goto L1f
        Lc:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "a "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r4 = " element"
            r1.append(r4)
            java.lang.String r4 = r1.toString()
        L1f:
            r0.append(r4)
            java.lang.String r4 = " at line "
            r0.append(r4)
            r0.append(r5)
            java.lang.String r4 = ": "
            r0.append(r4)
            r0.append(r6)
            java.lang.String r4 = r0.toString()
            r3.<init>(r4)
            r3.lineNr = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.easy2pay.XMLParseException.<init>(java.lang.String, int, java.lang.String):void");
    }

    public int getLineNr() {
        return this.lineNr;
    }
}
