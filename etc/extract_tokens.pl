#!/usr/bin/perl

# decode the list of tokens in a GW-BASIC interpreter
# @(#) $Id$
# 2012-09-28, Georg Fischer
#
# Activation:
#   perl extract_tokens.pl +offset infile
#   perl extract_tokens.pl +8b1b   basic_abs
#
# The offset must be the exact position of the first token 
# (without the leading letter "A") in the token list.
# Like tokenlist.pl, but with no array of letter offsets,
# and with special character tokens.

use strict;

  my $dummy = <<'GFis';
from Olivett M20 basic_abs:
+  45
  8a00: d4 6b  8  4 5e 68 f0 69  46 69 6e  4 84 6b 66  5  Tk..^hpiFin..kf.
  8a10: fc 77 f0  3 88 6a  a  5  24 58 ca 75 22 7b 38  5  |wp..j..$XJu"{8.
  8a20:  a 58 f0 6b 5e 77 ea 57  66 77 76 69 6e  5 32  5  .Xpk^wjWfwvin.2.
  8a30: 36  5 3e  5 98    16 6a  ba 6a 72 7a e2 6a c8 7b  6.>....j:jrzbjH{
  8a40: 76 68 26 68 2a 68 2e 68  32 6d 22 4e c8 57 92 22  vh&h*h.h2m"NHW."
  8a50: d6 23    23 96 28 ba 69  6a 24 c4 7d  8 7d 78 44  V#.#.(:ij$D}.}xD
  8a60: 1e 41 94 59 4a 40 5e 1b  62 1f 46 1f 42 1a e4 19  .A.YJ@^.b.F.B.d.
  8a70: 28 1a 34 41 b6 41 d0 40  28 41 a8 1c  8 1c  4 1a  (.4A6AP@(A(.....
  8a80: 92 45  a 43 e0 43 36 43  6e 43 b2 42 ea 4a 5c 4d  .E.C`C6CnC2BjJ\M
  8a90: 3e 14 7c 14 b8 14 c2 2d  48 2c a8 2d 2c 29 8c 2c  >.|.8.B-H,(-,).,
  8aa0: 82 29 84 29 9c 29 94 29  a4 29 ac 29 b4 16 e2 77  .).).).)$),)4.bw
  8ab0: 3e 74 aa 13 ec 11 1e 14  f2 13 fc 14 12 7b 2c 14  >t*.l...r.|..{,.
  8ac0: 5c 11 12 11 18 74 a4 2c  c0 2c e6 2d  8 1e 36 17  \....t$,@,f-..6.
  8ad0: 9e 17 a2 17 a6 3d 1c 3d  e0 3e  a 17 7a 17 7e 17  ..".&=.=`>..z.~.
  8ae0: 82 4d 22 4d 30 59 72  c  f6  d  7  d  8  d 56  d  .M"M0Yr.v.....V.
  8af0: 88  d b1  d c7  d d9  d  de  e  8  e  9  e  e  e  ..1.G.Y.^.......

  8b00: 42  e 5b  e 6e  e 81  e  ad  e ae  e ec  f 35  f  B.[.n...-...l.5.
  8b10: 51  f 5a  f 64  f 83  f  87  f 88 4e c4 f2 42 d3  Q.Z.d......NDrBS
  8b20:  6 54 ce  e 53 c3 15 55  54 cf a9       4c 4f 53  .TN.SC.UTO)..LOS
  8b30: c5 c1 4f 4e d4 9a 4c 45  41 d2 92 49 4e d4 1c 53  EAONT.LEAR.INT.S
  8b40: 4e c7 1d 44 42 cc 1e 56  c9 20 56 d3 21 56 c4 22  NG.DBL.VI VS!VD"
  8b50: 4f d3  c 48 52 a4 16 41  4c cc b4 4f 4d 4d 4f ce  OS.HR$.ALL4OMMON
  8b60: b6 48 41 49 ce b7 49 52  43 4c c5 cf 4c d3 d0 55  6HAIN7IRCLEOLSPU
  8b70: 52 53 4f d2 d1 4f 4c 4f  d2 cb    41 54 c1 84 49  RSORQOLORK.ATA.I
  8b80: cd 86 45 46 53 54 d2 ab  45 46 49 4e d4 ac 45 46  M.EFSTR+EFINT,EF
  8b90: 53 4e c7 ad 45 46 44 42  cc ae 45 c6 98 45 4c 45  SNG-EFDBL.EF.ELE
  8ba0: 54 c5 a8 41 54 45 a4 d8  52 41 d7 b0    4e c4 81  TE(ATE$XRAW0.ND.
  8bb0: 4c 53 c5 a0 52 41 53 c5  a4 44 49 d4 a5 52 52 4f  LSE RASE$DIT%RRO
  8bc0: d2 a6 52 cc e0 52 d2 e1  58 45 c3 c5 58 d0  b 4f  R&RL`RRaXECEXP.O
  8bd0: c6 23 51 d6 f5    4f d2  82 49 45 4c c4 be 49 4c  F#QVu.OR.IELD>IL
  8be0: 45 d3 c4 ce dd 52 c5  f  49 d8 1f    4f 54 cf 89  ESDN]RE.IX..OTO.
  8bf0: 4f 20 54 cf 89 4f 53 55  c2 8d 45 d4 bf    45 58  O TO.OSUB.ET?.EX
+  46
  8c00: a4 1a    4e 50 55 d4 85  c6 8b 4e 4b 45 59 a4 da  $..NPUT.F.NKEY$Z
  8c10: 4e 53 54 d2 e4 4e d4  5  4e d0 10 4d d0 f6 53 45  NSTRdNT.NP.MPvSE
  8c20: d4 9b 52 45 53 45 d4 bc  45 45 c5 2b       49 4c  T.RESET<EEE+..IL
  8c30: cc c7    45 d4 88 49 4e  c5 af 4f 41 c4 c2 53 45  LG.ET.INE/OADBSE
  8c40: d4 c8 50 52 49 4e d4 9c  4c 49 53 d4 9d 50 4f d3  THPRINT.LIST.POS
  8c50: 1b 49 53 d4 93 4f c7  a  4f c3 24 45 ce 12 45 46  .IST.OG.OC$EN.EF
  8c60: 54 a4  1 4f c6 25    45  52 47 c5 c3 4f c4 f7 4b  T$.OF%.ERGECODwK
  8c70: 49 a4 26 4b 53 a4 27 4b  44 a4 28 49 44 a4  3     I$&KS$.KD$(ID$..
  8c80: 45 58 d4 83 55 4c cc 96  41 4d c5 c6 45 d7 94 4f  EXT.ULL.AMEFEW.O
  8c90: d4 df    ce 95 50 45 ce  bd d2 f3 43 54 a4 19 50  T_.N.PEN=RsCT$.P
  8ca0: 54 49 4f ce b8    55 d4  c0 4f 4b c5 99 52 49 4e  TION8.UT@OKE.RIN
  8cb0: d4 91 4f d3 11 45 45 cb  17 41 49 4e d4 cc 53 45  T.OS.EEK.AINTLSE
  8cc0: d4 cd 52 45 53 45 d4 ce  4f 49 4e d4 e6 4f 4c cc  TMRESETNOINTfOLL
  8cd0: 97       45 41 c4 87 55  ce 8a 45 53 54 4f 52 c5  ...EAD.UN.ESTORE
  8ce0: 8c 45 54 55 52 ce 8e 45  cd 8f 45 53 55 4d c5 a7  .ETURN.EM.ESUME.
  8cf0: 53 45 d4 c9 49 47 48 54  a4  2 4e c4  8 45 4e 55  SETIIGHT$.ND.ENU

  8d00: cd aa 41 4e 44 4f 4d 49  5a c5 b9 42 59 54 c5 9e  M*ANDOMIZE9BYTE.
  8d10:    54 4f d0 90 57 41 d0  a3 41 56 c5 ca 50 43 a8  .TOP.WAP#AVEJPC(
  8d20: de 54 45 d0 d7 47 ce  4  51 d2  7 49 ce  9 54 52  ^TEPWGN.QR.IN.TR
  8d30: a4 13 54 52 49 4e 47 a4  e2 50 41 43 45 a4 18 59  $.TRING$bPACE$.Y
  8d40: 53 54 45 cd bb 43 41 4c  45 d8 29 43 41 4c 45 d9  STEM;CALEX)CALEY
  8d50: 2a 43 41 4c c5 d2 52 d1  db    52 4f ce a1 52 4f  *CALERRQ[.RON!RO
  8d60: 46 c6 a2 41 42 a8 d6 cf  d4 48 45 ce d5 41 ce  d  FF.AB(VOTHENUAN.
  8d70: 49 4d 45 a4 d9    53 49  4e c7 e3 53 d2 dc    41  IME$Y.SINGcSR..A
  8d80: cc 14 41 52 50 54 d2 e7     49 44 54 c8 9f 48 49  L.ARPTRg.IDTH.HI
  8d90: 4c c5 b2 45 4e c4 b3 52  49 54 c5 b5 49 4e 44 4f  LE2END3RITE5INDO
  8da0: d7 ba 42 59 54 c5 b1     4f d2 f4          ab ed  W:BYTE1.ORt...+m
  8db0: ad ee aa ef af f0 de f1  dc f8 a7 e5 be ea bd eb  -n*o/p^q.x.e>j=k
  8dc0: bc ec    79 79 7c 7c 7f  50 46 3c 32 28 7a 7b 2d  <l.yy||#PF<2(z{-
  8dd0:  8       2c c0 1e 2c 2c  e6 2b  e 2b 20 2b 32 2b  ...,@.,,f+.+ +2+
  8de0: 44 2b 56 2a 7a 2a 96 2a  b2 2a 5c 2a ea 2a 16 2a  D+V*z*.*2*.*j*.*
  8df0: 2a 2a 20 71 96 2a  a     4e 45 58 54 20 77 69 74  ** q.*..NEXT wit
+  47
GFis
	
	my $debug = 1;
	my $identical = 1;
	my $offset = lc(shift(@ARGV));
	$offset =~ s{[^0-9a-f]}{}g; # remove non-hex characters
	$offset = hex("0x$offset");
	my $infile = shift (@ARGV);
	open(IN, "<", $infile) || die "cannot read $infile"; 
	binmode (IN);
	seek (IN, $offset, 1);
	my $buffer = "";
	read(IN, $buffer, 0x1000);
	
	my $ilet = 0;
	my $oflet = 0; # position of "ND" in the M20 example
	# start with the 26 uppercase letters
	while ($ilet < 26) {
		my $letter = chr(ord('A') + $ilet);
		$oflet = &decode_letter($letter, $oflet);
		$ilet ++;
	} # while $ilet
	# continue with one set of special characters
	$oflet = &decode_special($oflet);

sub put_token {
	my ($token_code, $token) = @_;
	if ($token eq "\\") {
		$token = "\\\\";
	}
	print sprintf("        tokenStrings[0x%02x] = \"%s\";\n", $token_code, $token);
} # put_token

sub decode_letter {
	my ($letter, $oflet) = @_;
	my $ibuf = $oflet;
	my $token = $letter;
	my $token_code = 0;
	my $busy = 1;
	while ($busy) {
		my $byte = ord(substr($buffer, $ibuf, 1));
		if ($byte == 0) {
			$busy = 0;
		} elsif ($byte < 0x80) {
			$token .= chr($byte);
		} else {
			$token .= chr($byte & 0x7f);
			$ibuf ++;
			$token_code = ord(substr($buffer, $ibuf, 1));
			&put_token($token_code, $token);
			$token = $letter;
		}
		$ibuf ++;
	} # while $busy
	return $ibuf;
} # decode_letter 

sub decode_special {
	my ($oflet) = @_;
	my $ibuf = $oflet;
	my $token = 0;
	my $token_code = 0;
	my $busy = 1;
	my $byte = ord(substr($buffer, $ibuf, 1));
	while ($byte != 0) {
		$token = chr($byte & 0x7f);
		$ibuf ++;
		$token_code = ord(substr($buffer, $ibuf, 1));
		&put_token($token_code, $token);
		$ibuf ++;
		$byte = ord(substr($buffer, $ibuf, 1));
	} # while $busy
	return $ibuf;
} # decode_special
__DATA__
  8d70: 49 4d 45 a4 d9    53 49  4e c7 e3 53 d2 dc    41  IME$Y.SINGcSR..A
  8d80: cc 14 41 52 50 54 d2 e7     49 44 54 c8 9f 48 49  L.ARPTRg.IDTH.HI
  8d90: 4c c5 b2 45 4e c4 b3 52  49 54 c5 b5 49 4e 44 4f  LE2END3RITE5INDO
  8da0: d7 ba 42 59 54 c5 b1     4f d2 f4          ab ed  W:BYTE1.ORt...+m
  8db0: ad ee aa ef af f0 de f1  dc f8 a7 e5 be ea bd eb  -n*o/p^q.x.e>j=k
  8dc0: bc ec    79 79 7c 7c 7f  50 46 3c 32 28 7a 7b 2d  <l.yy||#PF<2(z{-
  8dd0:  8       2c c0 1e 2c 2c  e6 2b  e 2b 20 2b 32 2b  ...,@.,,f+.+ +2+
  8de0: 44 2b 56 2a 7a 2a 96 2a  b2 2a 5c 2a ea 2a 16 2a  D+V*z*.*2*.*j*.*
  8df0: 2a 2a 20 71 96 2a  a     4e 45 58 54 20 77 69 74  ** q.*..NEXT wit
