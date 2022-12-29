package com.android.easy2pay;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import kotlin.text.Typography;
import org.apache.commons.httpclient.cookie.CookieSpec;

/* loaded from: classes3.dex */
class XMLElement {
    public static final int NANOXML_MAJOR_VERSION = 2;
    public static final int NANOXML_MINOR_VERSION = 2;
    static final long serialVersionUID = 6685035139346394777L;
    private Hashtable attributes;
    private char charReadTooMuch;
    private Vector children;
    private String contents;
    private Hashtable entities;
    private boolean ignoreCase;
    private boolean ignoreWhitespace;
    private int lineNr;
    private String name;
    private int parserLineNr;
    private Reader reader;

    public XMLElement() {
        this(new Hashtable(), false, true, true);
    }

    public XMLElement(Hashtable hashtable) {
        this(hashtable, false, true, true);
    }

    public XMLElement(boolean z) {
        this(new Hashtable(), z, true, true);
    }

    public XMLElement(Hashtable hashtable, boolean z) {
        this(hashtable, z, true, true);
    }

    public XMLElement(Hashtable hashtable, boolean z, boolean z2) {
        this(hashtable, z, true, z2);
    }

    protected XMLElement(Hashtable hashtable, boolean z, boolean z2, boolean z3) {
        this.ignoreWhitespace = z;
        this.ignoreCase = z3;
        this.name = null;
        this.contents = "";
        this.attributes = new Hashtable();
        this.children = new Vector();
        this.entities = hashtable;
        this.lineNr = 0;
        Enumeration keys = this.entities.keys();
        while (keys.hasMoreElements()) {
            Object nextElement = keys.nextElement();
            Object obj = this.entities.get(nextElement);
            if (obj instanceof String) {
                this.entities.put(nextElement, ((String) obj).toCharArray());
            }
        }
        if (z2) {
            this.entities.put("amp", new char[]{Typography.amp});
            this.entities.put("quot", new char[]{Typography.quote});
            this.entities.put("apos", new char[]{'\''});
            this.entities.put("lt", new char[]{Typography.less});
            this.entities.put("gt", new char[]{Typography.greater});
        }
    }

    public void addChild(XMLElement xMLElement) {
        this.children.addElement(xMLElement);
    }

    public void setAttribute(String str, Object obj) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        this.attributes.put(str, obj.toString());
    }

    public void addProperty(String str, Object obj) {
        setAttribute(str, obj);
    }

    public void setIntAttribute(String str, int i) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        this.attributes.put(str, Integer.toString(i));
    }

    public void addProperty(String str, int i) {
        setIntAttribute(str, i);
    }

    public void setDoubleAttribute(String str, double d) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        this.attributes.put(str, Double.toString(d));
    }

    public void addProperty(String str, double d) {
        setDoubleAttribute(str, d);
    }

    public int countChildren() {
        return this.children.size();
    }

    public Enumeration enumerateAttributeNames() {
        return this.attributes.keys();
    }

    public Enumeration enumeratePropertyNames() {
        return enumerateAttributeNames();
    }

    public Enumeration enumerateChildren() {
        return this.children.elements();
    }

    public Vector getChildren() {
        try {
            return (Vector) this.children.clone();
        } catch (Exception unused) {
            return null;
        }
    }

    public String getContents() {
        return getContent();
    }

    public String getContent() {
        return this.contents;
    }

    public int getLineNr() {
        return this.lineNr;
    }

    public Object getAttribute(String str) {
        return getAttribute(str, null);
    }

    public Object getAttribute(String str, Object obj) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        Object obj2 = this.attributes.get(str);
        return obj2 == null ? obj : obj2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object getAttribute(String str, Hashtable hashtable, String str2, boolean z) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        Object obj = this.attributes.get(str);
        if (obj != 0) {
            str2 = obj;
        }
        Object obj2 = hashtable.get(str2);
        if (obj2 == null) {
            if (z) {
                return str2;
            }
            throw invalidValue(str, str2);
        }
        return obj2;
    }

    public String getStringAttribute(String str) {
        return getStringAttribute(str, null);
    }

    public String getStringAttribute(String str, String str2) {
        return (String) getAttribute(str, str2);
    }

    public String getStringAttribute(String str, Hashtable hashtable, String str2, boolean z) {
        return (String) getAttribute(str, hashtable, str2, z);
    }

    public int getIntAttribute(String str) {
        return getIntAttribute(str, 0);
    }

    public int getIntAttribute(String str, int i) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        String str2 = (String) this.attributes.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            return Integer.parseInt(str2);
        } catch (NumberFormatException unused) {
            throw invalidValue(str, str2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getIntAttribute(String str, Hashtable hashtable, String str2, boolean z) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        Object obj = this.attributes.get(str);
        if (obj != 0) {
            str2 = obj;
        }
        try {
            Integer num = (Integer) hashtable.get(str2);
            if (num == null) {
                if (!z) {
                    throw invalidValue(str, str2);
                }
                try {
                    num = Integer.valueOf(str2);
                } catch (NumberFormatException unused) {
                    throw invalidValue(str, str2);
                }
            }
            return num.intValue();
        } catch (ClassCastException unused2) {
            throw invalidValueSet(str);
        }
    }

    public double getDoubleAttribute(String str) {
        return getDoubleAttribute(str, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
    }

    public double getDoubleAttribute(String str, double d) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        String str2 = (String) this.attributes.get(str);
        if (str2 == null) {
            return d;
        }
        try {
            return Double.valueOf(str2).doubleValue();
        } catch (NumberFormatException unused) {
            throw invalidValue(str, str2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public double getDoubleAttribute(String str, Hashtable hashtable, String str2, boolean z) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        Object obj = this.attributes.get(str);
        if (obj != 0) {
            str2 = obj;
        }
        try {
            Double d = (Double) hashtable.get(str2);
            if (d == null) {
                if (!z) {
                    throw invalidValue(str, str2);
                }
                try {
                    d = Double.valueOf(str2);
                } catch (NumberFormatException unused) {
                    throw invalidValue(str, str2);
                }
            }
            return d.doubleValue();
        } catch (ClassCastException unused2) {
            throw invalidValueSet(str);
        }
    }

    public boolean getBooleanAttribute(String str, String str2, String str3, boolean z) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        Object obj = this.attributes.get(str);
        if (obj == null) {
            return z;
        }
        if (obj.equals(str2)) {
            return true;
        }
        if (obj.equals(str3)) {
            return false;
        }
        throw invalidValue(str, (String) obj);
    }

    public int getIntProperty(String str, Hashtable hashtable, String str2) {
        return getIntAttribute(str, hashtable, str2, false);
    }

    public String getProperty(String str) {
        return getStringAttribute(str);
    }

    public String getProperty(String str, String str2) {
        return getStringAttribute(str, str2);
    }

    public int getProperty(String str, int i) {
        return getIntAttribute(str, i);
    }

    public double getProperty(String str, double d) {
        return getDoubleAttribute(str, d);
    }

    public boolean getProperty(String str, String str2, String str3, boolean z) {
        return getBooleanAttribute(str, str2, str3, z);
    }

    public Object getProperty(String str, Hashtable hashtable, String str2) {
        return getAttribute(str, hashtable, str2, false);
    }

    public String getStringProperty(String str, Hashtable hashtable, String str2) {
        return getStringAttribute(str, hashtable, str2, false);
    }

    public int getSpecialIntProperty(String str, Hashtable hashtable, String str2) {
        return getIntAttribute(str, hashtable, str2, true);
    }

    public double getSpecialDoubleProperty(String str, Hashtable hashtable, String str2) {
        return getDoubleAttribute(str, hashtable, str2, true);
    }

    public String getName() {
        return this.name;
    }

    public String getTagName() {
        return getName();
    }

    public void parseFromReader(Reader reader) throws IOException, XMLParseException {
        parseFromReader(reader, 1);
    }

    public void parseFromReader(Reader reader, int i) throws IOException, XMLParseException {
        this.charReadTooMuch = (char) 0;
        this.reader = reader;
        this.parserLineNr = i;
        while (scanWhitespace() == '<') {
            char readChar = readChar();
            if (readChar == '!' || readChar == '?') {
                skipSpecialTag(0);
            } else {
                unreadChar(readChar);
                scanElement(this);
                return;
            }
        }
        throw expectedInput("<");
    }

    public void parseString(String str) throws XMLParseException {
        try {
            parseFromReader(new StringReader(str), 1);
        } catch (IOException unused) {
        }
    }

    public void parseString(String str, int i) throws XMLParseException {
        parseString(str.substring(i));
    }

    public void parseString(String str, int i, int i2) throws XMLParseException {
        parseString(str.substring(i, i2));
    }

    public void parseString(String str, int i, int i2, int i3) throws XMLParseException {
        try {
            parseFromReader(new StringReader(str.substring(i, i2)), i3);
        } catch (IOException unused) {
        }
    }

    public void parseCharArray(char[] cArr, int i, int i2) throws XMLParseException {
        parseCharArray(cArr, i, i2, 1);
    }

    public void parseCharArray(char[] cArr, int i, int i2, int i3) throws XMLParseException {
        try {
            parseFromReader(new CharArrayReader(cArr, i, i2), i3);
        } catch (IOException unused) {
        }
    }

    public void removeChild(XMLElement xMLElement) {
        this.children.removeElement(xMLElement);
    }

    public void removeAttribute(String str) {
        if (this.ignoreCase) {
            str = str.toUpperCase();
        }
        this.attributes.remove(str);
    }

    public void removeProperty(String str) {
        removeAttribute(str);
    }

    public void removeChild(String str) {
        removeAttribute(str);
    }

    protected XMLElement createAnotherElement() {
        return new XMLElement(this.entities, this.ignoreWhitespace, false, this.ignoreCase);
    }

    public void setContent(String str) {
        this.contents = str;
    }

    public void setTagName(String str) {
        setName(str);
    }

    public void setName(String str) {
        this.name = str;
    }

    public String toString() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
            write(outputStreamWriter);
            outputStreamWriter.flush();
            return new String(byteArrayOutputStream.toByteArray());
        } catch (IOException unused) {
            return super.toString();
        }
    }

    public void write(Writer writer) throws IOException {
        if (this.name == null) {
            writeEncoded(writer, this.contents);
            return;
        }
        writer.write(60);
        writer.write(this.name);
        if (!this.attributes.isEmpty()) {
            Enumeration keys = this.attributes.keys();
            while (keys.hasMoreElements()) {
                writer.write(32);
                String str = (String) keys.nextElement();
                writer.write(str);
                writer.write(61);
                writer.write(34);
                writeEncoded(writer, (String) this.attributes.get(str));
                writer.write(34);
            }
        }
        String str2 = this.contents;
        if (str2 != null && str2.length() > 0) {
            writer.write(62);
            writeEncoded(writer, this.contents);
            writer.write(60);
            writer.write(47);
            writer.write(this.name);
            writer.write(62);
        } else if (this.children.isEmpty()) {
            writer.write(47);
            writer.write(62);
        } else {
            writer.write(62);
            Enumeration enumerateChildren = enumerateChildren();
            while (enumerateChildren.hasMoreElements()) {
                ((XMLElement) enumerateChildren.nextElement()).write(writer);
            }
            writer.write(60);
            writer.write(47);
            writer.write(this.name);
            writer.write(62);
        }
    }

    protected void writeEncoded(Writer writer, String str) throws IOException {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\"') {
                writer.write(38);
                writer.write(113);
                writer.write(117);
                writer.write(111);
                writer.write(116);
                writer.write(59);
            } else if (charAt == '<') {
                writer.write(38);
                writer.write(108);
                writer.write(116);
                writer.write(59);
            } else if (charAt == '>') {
                writer.write(38);
                writer.write(103);
                writer.write(116);
                writer.write(59);
            } else if (charAt == '&') {
                writer.write(38);
                writer.write(97);
                writer.write(109);
                writer.write(112);
                writer.write(59);
            } else if (charAt == '\'') {
                writer.write(38);
                writer.write(97);
                writer.write(112);
                writer.write(111);
                writer.write(115);
                writer.write(59);
            } else if (charAt < ' ' || charAt > '~') {
                writer.write(38);
                writer.write(35);
                writer.write(120);
                writer.write(Integer.toString(charAt, 16));
                writer.write(59);
            } else {
                writer.write(charAt);
            }
        }
    }

    protected void scanIdentifier(StringBuffer stringBuffer) throws IOException {
        while (true) {
            char readChar = readChar();
            if ((readChar < 'A' || readChar > 'Z') && ((readChar < 'a' || readChar > 'z') && ((readChar < '0' || readChar > '9') && readChar != '_' && readChar != '.' && readChar != ':' && readChar != '-' && readChar <= '~'))) {
                unreadChar(readChar);
                return;
            }
            stringBuffer.append(readChar);
        }
    }

    protected char scanWhitespace() throws IOException {
        while (true) {
            char readChar = readChar();
            if (readChar != '\t' && readChar != '\n' && readChar != '\r' && readChar != ' ') {
                return readChar;
            }
        }
    }

    protected char scanWhitespace(StringBuffer stringBuffer) throws IOException {
        while (true) {
            char readChar = readChar();
            if (readChar != '\t' && readChar != '\n') {
                if (readChar == '\r') {
                    continue;
                } else if (readChar != ' ') {
                    return readChar;
                }
            }
            stringBuffer.append(readChar);
        }
    }

    protected void scanString(StringBuffer stringBuffer) throws IOException {
        char readChar = readChar();
        if (readChar != '\'' && readChar != '\"') {
            throw expectedInput("' or \"");
        }
        while (true) {
            char readChar2 = readChar();
            if (readChar2 == readChar) {
                return;
            }
            if (readChar2 == '&') {
                resolveEntity(stringBuffer);
            } else {
                stringBuffer.append(readChar2);
            }
        }
    }

    protected void scanPCData(StringBuffer stringBuffer) throws IOException {
        while (true) {
            char readChar = readChar();
            if (readChar == '<') {
                char readChar2 = readChar();
                if (readChar2 == '!') {
                    checkCDATA(stringBuffer);
                } else {
                    unreadChar(readChar2);
                    return;
                }
            } else if (readChar == '&') {
                resolveEntity(stringBuffer);
            } else {
                stringBuffer.append(readChar);
            }
        }
    }

    protected boolean checkCDATA(StringBuffer stringBuffer) throws IOException {
        char readChar = readChar();
        if (readChar != '[') {
            unreadChar(readChar);
            skipSpecialTag(0);
            return false;
        } else if (!checkLiteral("CDATA[")) {
            skipSpecialTag(1);
            return false;
        } else {
            while (true) {
                int i = 0;
                while (i < 3) {
                    char readChar2 = readChar();
                    if (readChar2 != '>') {
                        if (readChar2 != ']') {
                            for (int i2 = 0; i2 < i; i2++) {
                                stringBuffer.append(']');
                            }
                            stringBuffer.append(readChar2);
                        } else if (i < 2) {
                            i++;
                        } else {
                            stringBuffer.append(']');
                            stringBuffer.append(']');
                        }
                    } else if (i < 2) {
                        for (int i3 = 0; i3 < i; i3++) {
                            stringBuffer.append(']');
                        }
                        stringBuffer.append(Typography.greater);
                    } else {
                        i = 3;
                    }
                }
                return true;
            }
        }
    }

    protected void skipComment() throws IOException {
        int i;
        loop0: while (true) {
            while (i > 0) {
                i = readChar() == '-' ? i - 1 : 2;
            }
        }
        if (readChar() != '>') {
            throw expectedInput(">");
        }
    }

    protected void skipSpecialTag(int i) throws IOException {
        int i2 = 1;
        if (i == 0) {
            char readChar = readChar();
            if (readChar != '[') {
                if (readChar == '-') {
                    char readChar2 = readChar();
                    if (readChar2 != '[') {
                        if (readChar2 == ']') {
                            i--;
                        } else if (readChar2 == '-') {
                            skipComment();
                            return;
                        }
                    }
                }
            }
            i++;
        }
        int i3 = i;
        while (true) {
            char c = 0;
            while (i2 > 0) {
                char readChar3 = readChar();
                if (c == 0) {
                    if (readChar3 == '\"' || readChar3 == '\'') {
                        c = readChar3;
                    } else if (i3 <= 0) {
                        if (readChar3 == '<') {
                            i2++;
                        } else if (readChar3 == '>') {
                            i2--;
                        }
                    }
                    if (readChar3 == '[') {
                        i3++;
                    } else if (readChar3 == ']') {
                        i3--;
                    }
                } else if (readChar3 == c) {
                    break;
                }
            }
            return;
        }
    }

    protected boolean checkLiteral(String str) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (readChar() != str.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    protected char readChar() throws IOException {
        char c = this.charReadTooMuch;
        if (c != 0) {
            this.charReadTooMuch = (char) 0;
            return c;
        }
        int read = this.reader.read();
        if (read >= 0) {
            if (read == 10) {
                this.parserLineNr++;
                return '\n';
            }
            return (char) read;
        }
        throw unexpectedEndOfData();
    }

    protected void scanElement(XMLElement xMLElement) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        scanIdentifier(stringBuffer);
        String stringBuffer2 = stringBuffer.toString();
        xMLElement.setName(stringBuffer2);
        char scanWhitespace = scanWhitespace();
        while (scanWhitespace != '>' && scanWhitespace != '/') {
            stringBuffer.setLength(0);
            unreadChar(scanWhitespace);
            scanIdentifier(stringBuffer);
            String stringBuffer3 = stringBuffer.toString();
            if (scanWhitespace() != '=') {
                throw expectedInput("=");
            }
            unreadChar(scanWhitespace());
            stringBuffer.setLength(0);
            scanString(stringBuffer);
            xMLElement.setAttribute(stringBuffer3, stringBuffer);
            scanWhitespace = scanWhitespace();
        }
        if (scanWhitespace == '/') {
            if (readChar() != '>') {
                throw expectedInput(">");
            }
            return;
        }
        stringBuffer.setLength(0);
        char scanWhitespace2 = scanWhitespace(stringBuffer);
        if (scanWhitespace2 == '<') {
            while (true) {
                scanWhitespace2 = readChar();
                if (scanWhitespace2 == '!') {
                    if (checkCDATA(stringBuffer)) {
                        scanPCData(stringBuffer);
                        break;
                    }
                    scanWhitespace2 = scanWhitespace(stringBuffer);
                    if (scanWhitespace2 != '<') {
                        unreadChar(scanWhitespace2);
                        scanPCData(stringBuffer);
                        break;
                    }
                } else {
                    stringBuffer.setLength(0);
                    break;
                }
            }
        } else {
            unreadChar(scanWhitespace2);
            scanPCData(stringBuffer);
        }
        if (stringBuffer.length() == 0) {
            while (scanWhitespace2 != '/') {
                if (scanWhitespace2 == '!') {
                    if (readChar() != '-') {
                        throw expectedInput("Comment or Element");
                    }
                    if (readChar() != '-') {
                        throw expectedInput("Comment or Element");
                    }
                    skipComment();
                } else {
                    unreadChar(scanWhitespace2);
                    XMLElement createAnotherElement = createAnotherElement();
                    scanElement(createAnotherElement);
                    xMLElement.addChild(createAnotherElement);
                }
                if (scanWhitespace() != '<') {
                    throw expectedInput("<");
                }
                scanWhitespace2 = readChar();
            }
            unreadChar(scanWhitespace2);
        } else if (this.ignoreWhitespace) {
            xMLElement.setContent(stringBuffer.toString().trim());
        } else {
            xMLElement.setContent(stringBuffer.toString());
        }
        if (readChar() != '/') {
            throw expectedInput(CookieSpec.PATH_DELIM);
        }
        unreadChar(scanWhitespace());
        if (!checkLiteral(stringBuffer2)) {
            throw expectedInput(stringBuffer2);
        }
        if (scanWhitespace() != '>') {
            throw expectedInput(">");
        }
    }

    protected void resolveEntity(StringBuffer stringBuffer) throws IOException {
        int parseInt;
        StringBuffer stringBuffer2 = new StringBuffer();
        while (true) {
            char readChar = readChar();
            if (readChar == ';') {
                break;
            }
            stringBuffer2.append(readChar);
        }
        String stringBuffer3 = stringBuffer2.toString();
        if (stringBuffer3.charAt(0) == '#') {
            try {
                if (stringBuffer3.charAt(1) == 'x') {
                    parseInt = Integer.parseInt(stringBuffer3.substring(2), 16);
                } else {
                    parseInt = Integer.parseInt(stringBuffer3.substring(1), 10);
                }
                stringBuffer.append((char) parseInt);
                return;
            } catch (NumberFormatException unused) {
                throw unknownEntity(stringBuffer3);
            }
        }
        char[] cArr = (char[]) this.entities.get(stringBuffer3);
        if (cArr == null) {
            throw unknownEntity(stringBuffer3);
        }
        stringBuffer.append(cArr);
    }

    protected void unreadChar(char c) {
        this.charReadTooMuch = c;
    }

    protected XMLParseException invalidValueSet(String str) {
        return new XMLParseException(getName(), this.parserLineNr, "Invalid value set (entity name = \"" + str + "\")");
    }

    protected XMLParseException invalidValue(String str, String str2) {
        return new XMLParseException(getName(), this.parserLineNr, "Attribute \"" + str + "\" does not contain a valid value (\"" + str2 + "\")");
    }

    protected XMLParseException unexpectedEndOfData() {
        return new XMLParseException(getName(), this.parserLineNr, "Unexpected end of data reached");
    }

    protected XMLParseException syntaxError(String str) {
        return new XMLParseException(getName(), this.parserLineNr, "Syntax error while parsing " + str);
    }

    protected XMLParseException expectedInput(String str) {
        return new XMLParseException(getName(), this.parserLineNr, "Expected: " + str);
    }

    protected XMLParseException unknownEntity(String str) {
        return new XMLParseException(getName(), this.parserLineNr, "Unknown or invalid entity: &" + str + ";");
    }
}
