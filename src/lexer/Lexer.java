/* The following code was generated by JFlex 1.4.3 on 10/30/22, 7:27 PM */

package lexer;
import lexer.Token;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 10/30/22, 7:27 PM from the specification file
 * <tt>./src/lexer/Lexer.flex</tt>
 */
class Lexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 2;
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\3\1\57\1\51"+
    "\2\0\1\57\1\61\1\17\1\52\1\52\1\5\1\53\1\52\1\16"+
    "\1\14\1\4\1\10\7\11\2\7\1\52\1\52\1\56\1\54\1\55"+
    "\1\52\1\0\4\13\1\15\1\13\21\6\1\12\2\6\1\52\1\20"+
    "\1\52\1\57\1\6\1\0\1\24\1\27\1\32\1\36\1\30\1\37"+
    "\1\43\1\34\1\35\1\6\1\31\1\40\1\41\1\21\1\26\1\47"+
    "\1\6\1\22\1\33\1\23\1\25\1\50\1\45\1\42\1\46\1\44"+
    "\1\52\1\60\1\52\1\52\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\2\3\1\4\2\5\2\3\1\1"+
    "\17\4\1\6\6\3\1\7\1\10\1\11\1\12\1\0"+
    "\1\1\2\0\1\5\1\1\1\5\2\0\17\4\2\13"+
    "\6\4\1\14\1\15\1\16\1\17\1\12\2\0\1\5"+
    "\1\1\1\0\2\5\30\4\1\12\1\0\1\2\1\5"+
    "\1\0\1\20\27\4";

  private static int [] zzUnpackAction() {
    int [] result = new int[136];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\62\0\144\0\226\0\144\0\310\0\372\0\u012c"+
    "\0\u015e\0\u0190\0\u01c2\0\u01f4\0\u0226\0\u0258\0\u028a\0\u02bc"+
    "\0\u02ee\0\u0320\0\u0352\0\u0384\0\u03b6\0\u03e8\0\u041a\0\u044c"+
    "\0\u047e\0\u04b0\0\u04e2\0\u0514\0\144\0\144\0\u0546\0\u0578"+
    "\0\u05aa\0\u05dc\0\u060e\0\u0640\0\u0672\0\144\0\u06a4\0\u06d6"+
    "\0\u0708\0\u01c2\0\u073a\0\u076c\0\u079e\0\u07d0\0\u0802\0\u0834"+
    "\0\u0866\0\u0898\0\u08ca\0\u08fc\0\u092e\0\u0960\0\u0992\0\u09c4"+
    "\0\u09f6\0\u0a28\0\u0a5a\0\u0a8c\0\u0abe\0\u0af0\0\u0b22\0\u012c"+
    "\0\u0b54\0\u0b86\0\u0bb8\0\u0bea\0\u0c1c\0\u0c4e\0\u0c80\0\144"+
    "\0\144\0\144\0\144\0\u0cb2\0\u0ce4\0\u0d16\0\u079e\0\u0d48"+
    "\0\u0d7a\0\u0d7a\0\u0802\0\u0dac\0\u0dde\0\u0e10\0\u0e42\0\u0e74"+
    "\0\u0ea6\0\u0ed8\0\u0f0a\0\u0f3c\0\u0f6e\0\u0fa0\0\u0fd2\0\u1004"+
    "\0\u1036\0\u1068\0\u109a\0\u10cc\0\u10fe\0\u1130\0\u1162\0\u1194"+
    "\0\u11c6\0\u11f8\0\u122a\0\u125c\0\u128e\0\u0ce4\0\u12c0\0\u12f2"+
    "\0\u0d7a\0\u1324\0\u1356\0\u1388\0\u13ba\0\u13ec\0\u141e\0\u1450"+
    "\0\u1482\0\u14b4\0\u14e6\0\u1518\0\u154a\0\u157c\0\u15ae\0\u15e0"+
    "\0\u1612\0\u1644\0\u1676\0\u16a8\0\u16da\0\u170c\0\u173e\0\u1770";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[136];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\2\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\11\2\10\1\13\1\10\1\14\1\15\1\3\1\10"+
    "\1\16\1\17\1\20\1\21\1\10\1\22\1\23\1\10"+
    "\1\24\1\25\1\10\1\26\1\27\1\30\1\31\2\10"+
    "\1\32\1\10\1\33\2\10\1\34\1\35\1\36\1\37"+
    "\1\7\1\40\1\41\1\7\1\42\1\43\1\44\2\3"+
    "\15\44\1\45\30\44\1\46\10\44\64\0\1\5\63\0"+
    "\1\47\1\50\46\0\1\36\61\0\1\36\13\0\6\10"+
    "\1\0\1\10\3\0\30\10\17\0\1\51\3\11\2\51"+
    "\1\52\1\51\3\0\30\51\17\0\1\51\1\53\2\54"+
    "\1\55\1\51\1\52\1\51\3\0\21\51\1\55\6\51"+
    "\20\0\3\56\66\0\1\36\35\0\2\36\4\0\2\57"+
    "\1\0\15\57\1\60\41\57\6\0\6\10\1\0\1\10"+
    "\3\0\7\10\1\61\20\10\17\0\6\10\1\0\1\10"+
    "\3\0\25\10\1\62\2\10\17\0\6\10\1\0\1\10"+
    "\3\0\4\10\1\63\23\10\17\0\6\10\1\0\1\10"+
    "\3\0\1\64\27\10\17\0\6\10\1\0\1\10\3\0"+
    "\1\10\1\65\26\10\17\0\6\10\1\0\1\10\3\0"+
    "\1\66\16\10\1\67\1\10\1\70\6\10\17\0\6\10"+
    "\1\0\1\10\3\0\3\10\1\67\1\10\1\71\5\10"+
    "\1\72\14\10\17\0\6\10\1\0\1\10\3\0\2\10"+
    "\1\73\10\10\1\74\1\75\7\10\1\76\3\10\17\0"+
    "\6\10\1\0\1\10\3\0\1\77\15\10\1\100\11\10"+
    "\17\0\6\10\1\0\1\10\3\0\5\10\1\101\1\10"+
    "\1\102\20\10\17\0\6\10\1\0\1\10\3\0\5\10"+
    "\1\103\11\10\1\104\10\10\17\0\6\10\1\0\1\10"+
    "\3\0\5\10\1\105\22\10\17\0\6\10\1\0\1\10"+
    "\3\0\5\10\1\63\22\10\17\0\6\10\1\0\1\10"+
    "\3\0\13\10\1\106\14\10\17\0\6\10\1\0\1\10"+
    "\3\0\5\10\1\107\22\10\64\0\2\36\61\0\1\36"+
    "\1\7\60\0\1\36\1\0\1\7\57\0\1\36\3\0"+
    "\1\36\55\0\1\36\4\0\1\36\1\44\2\0\15\44"+
    "\1\0\30\44\1\0\10\44\21\0\1\110\1\111\1\112"+
    "\25\0\1\113\10\0\1\47\1\114\1\5\57\47\5\115"+
    "\1\116\54\115\6\0\6\51\1\0\1\51\3\0\30\51"+
    "\20\0\3\53\2\0\1\52\53\0\1\51\1\53\2\54"+
    "\2\51\1\52\1\51\3\0\30\51\17\0\1\51\3\117"+
    "\1\51\1\117\1\0\1\117\3\0\3\51\1\117\2\51"+
    "\2\117\1\51\1\117\3\51\2\117\11\51\17\0\1\51"+
    "\3\56\2\51\1\0\1\120\3\0\7\51\1\120\20\51"+
    "\11\0\2\121\1\0\14\121\1\122\44\121\1\0\14\121"+
    "\1\123\4\57\36\121\6\0\6\10\1\0\1\10\3\0"+
    "\2\10\1\124\17\10\1\125\5\10\17\0\6\10\1\0"+
    "\1\10\3\0\26\10\1\126\1\10\17\0\6\10\1\0"+
    "\1\10\3\0\2\10\1\127\25\10\17\0\6\10\1\0"+
    "\1\10\3\0\12\10\1\130\1\10\1\131\13\10\17\0"+
    "\6\10\1\0\1\10\3\0\7\10\1\132\20\10\17\0"+
    "\6\10\1\0\1\10\3\0\4\10\1\133\23\10\17\0"+
    "\6\10\1\0\1\10\3\0\12\10\1\134\15\10\17\0"+
    "\6\10\1\0\1\10\3\0\2\10\1\135\25\10\17\0"+
    "\6\10\1\0\1\10\3\0\1\136\27\10\17\0\6\10"+
    "\1\0\1\10\3\0\3\10\1\103\24\10\17\0\6\10"+
    "\1\0\1\10\3\0\1\10\1\137\1\10\1\140\24\10"+
    "\17\0\6\10\1\0\1\10\3\0\5\10\1\141\22\10"+
    "\17\0\6\10\1\0\1\10\3\0\22\10\1\142\1\143"+
    "\4\10\17\0\6\10\1\0\1\10\3\0\14\10\1\144"+
    "\13\10\17\0\6\10\1\0\1\10\3\0\2\10\1\100"+
    "\25\10\17\0\6\10\1\0\1\10\3\0\4\10\1\145"+
    "\23\10\17\0\6\10\1\0\1\10\3\0\16\10\1\146"+
    "\11\10\17\0\6\10\1\0\1\10\3\0\1\10\1\100"+
    "\26\10\17\0\6\10\1\0\1\10\3\0\5\10\1\147"+
    "\22\10\17\0\6\10\1\0\1\10\3\0\1\150\27\10"+
    "\17\0\6\10\1\0\1\10\3\0\14\10\1\151\13\10"+
    "\17\0\6\10\1\0\1\10\3\0\14\10\1\152\2\10"+
    "\1\153\10\10\11\0\2\154\1\5\57\154\5\115\1\155"+
    "\60\115\1\156\1\155\54\115\6\0\1\51\3\157\2\51"+
    "\1\0\1\51\1\160\2\0\30\51\11\0\2\121\1\0"+
    "\14\121\1\161\42\121\6\0\6\10\1\0\1\10\3\0"+
    "\4\10\1\162\23\10\17\0\6\10\1\0\1\10\3\0"+
    "\14\10\1\163\13\10\17\0\6\10\1\0\1\10\3\0"+
    "\7\10\1\164\20\10\17\0\6\10\1\0\1\10\3\0"+
    "\5\10\1\100\22\10\17\0\6\10\1\0\1\10\3\0"+
    "\14\10\1\165\13\10\17\0\6\10\1\0\1\10\3\0"+
    "\5\10\1\166\22\10\17\0\6\10\1\0\1\10\3\0"+
    "\3\10\1\167\24\10\17\0\6\10\1\0\1\10\3\0"+
    "\20\10\1\100\7\10\17\0\6\10\1\0\1\10\3\0"+
    "\7\10\1\100\20\10\17\0\6\10\1\0\1\10\3\0"+
    "\7\10\1\162\20\10\17\0\6\10\1\0\1\10\3\0"+
    "\2\10\1\170\7\10\1\77\15\10\17\0\6\10\1\0"+
    "\1\10\3\0\4\10\1\171\23\10\17\0\6\10\1\0"+
    "\1\10\3\0\2\10\1\172\25\10\17\0\6\10\1\0"+
    "\1\10\3\0\1\10\1\77\26\10\17\0\6\10\1\0"+
    "\1\10\3\0\1\173\27\10\17\0\6\10\1\0\1\10"+
    "\3\0\7\10\1\174\20\10\17\0\6\10\1\0\1\10"+
    "\3\0\2\10\1\175\25\10\17\0\6\10\1\0\1\10"+
    "\3\0\6\10\1\151\21\10\17\0\6\10\1\0\1\10"+
    "\3\0\3\10\1\176\24\10\17\0\6\10\1\0\1\10"+
    "\3\0\3\10\1\77\24\10\17\0\6\10\1\0\1\10"+
    "\3\0\22\10\1\100\5\10\17\0\6\10\1\0\1\10"+
    "\3\0\17\10\1\134\10\10\17\0\6\10\1\0\1\10"+
    "\3\0\15\10\1\100\12\10\17\0\6\10\1\0\1\10"+
    "\3\0\3\10\1\177\24\10\11\0\2\154\1\0\57\154"+
    "\4\115\1\5\1\155\54\115\6\0\1\51\3\157\2\51"+
    "\1\0\1\51\3\0\30\51\20\0\3\157\56\0\6\10"+
    "\1\0\1\10\3\0\1\10\1\166\26\10\17\0\6\10"+
    "\1\0\1\10\3\0\12\10\1\200\15\10\17\0\6\10"+
    "\1\0\1\10\3\0\15\10\1\201\12\10\17\0\6\10"+
    "\1\0\1\10\3\0\22\10\1\142\5\10\17\0\6\10"+
    "\1\0\1\10\3\0\1\100\27\10\17\0\6\10\1\0"+
    "\1\10\3\0\10\10\1\100\17\10\17\0\6\10\1\0"+
    "\1\10\3\0\14\10\1\202\13\10\17\0\6\10\1\0"+
    "\1\10\3\0\11\10\1\77\16\10\17\0\6\10\1\0"+
    "\1\10\3\0\14\10\1\203\13\10\17\0\6\10\1\0"+
    "\1\10\3\0\7\10\1\152\20\10\17\0\6\10\1\0"+
    "\1\10\3\0\5\10\1\204\22\10\17\0\6\10\1\0"+
    "\1\10\3\0\11\10\1\205\16\10\17\0\6\10\1\0"+
    "\1\10\3\0\4\10\1\206\23\10\17\0\6\10\1\0"+
    "\1\10\3\0\2\10\1\106\25\10\17\0\6\10\1\0"+
    "\1\10\3\0\2\10\1\207\25\10\17\0\6\10\1\0"+
    "\1\10\3\0\7\10\1\204\20\10\17\0\6\10\1\0"+
    "\1\10\3\0\1\210\27\10\17\0\6\10\1\0\1\10"+
    "\3\0\11\10\1\100\16\10\17\0\6\10\1\0\1\10"+
    "\3\0\16\10\1\100\11\10\17\0\6\10\1\0\1\10"+
    "\3\0\13\10\1\100\14\10\17\0\6\10\1\0\1\10"+
    "\3\0\17\10\1\77\10\10\17\0\6\10\1\0\1\10"+
    "\3\0\7\10\1\103\20\10\17\0\6\10\1\0\1\10"+
    "\3\0\4\10\1\134\23\10\11\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[6050];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\1\11\27\1\2\11\7\1\1\11"+
    "\1\1\1\0\1\1\2\0\3\1\2\0\27\1\4\11"+
    "\1\1\2\0\2\1\1\0\33\1\1\0\2\1\1\0"+
    "\30\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[136];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */

  StringBuffer string = new StringBuffer();

  public String lexeme;

  private Token createToken(KindToken kindToken){
    return new Token(yytext(),kindToken, yyline+1);
  }

  private Token createStringValueToken(KindToken kindToken, String value){
    return new Token(value, kindToken, yyline+1);
  }

  private LexicalError createLexicalError(String message){
    return new LexicalError(message,yyline+1);
    
  }



  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 150) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Token yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 14: 
          { string.append('\t');
          }
        case 17: break;
        case 5: 
          { lexeme=yytext(); return createToken(KindToken.LITERAL);
          }
        case 18: break;
        case 11: 
          { lexeme=yytext(); return createToken(KindToken.RESERVED_WORD);
          }
        case 19: break;
        case 4: 
          { lexeme=yytext(); return createToken(KindToken.IDENTIFIER);
          }
        case 20: break;
        case 9: 
          { yybegin(YYINITIAL); lexeme=string.toString(); return createStringValueToken(KindToken.LITERAL, string.toString());
          }
        case 21: break;
        case 2: 
          { /* ignore */
          }
        case 22: break;
        case 8: 
          { string.append('\\');
          }
        case 23: break;
        case 15: 
          { string.append('\"');
          }
        case 24: break;
        case 13: 
          { string.append('\r');
          }
        case 25: break;
        case 12: 
          { string.append('\n');
          }
        case 26: break;
        case 3: 
          { lexeme=yytext(); return createToken(KindToken.OPERATOR);
          }
        case 27: break;
        case 7: 
          { string.append( yytext() );
          }
        case 28: break;
        case 6: 
          { string.setLength(0); yybegin(STRING);
          }
        case 29: break;
        case 10: 
          { /*Ignore*/
          }
        case 30: break;
        case 16: 
          { throw createLexicalError("Character constant too long for its type <"+yytext()+">");
          }
        case 31: break;
        case 1: 
          { throw createLexicalError("Illegal character <"+yytext()+">");
          }
        case 32: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return null;
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
