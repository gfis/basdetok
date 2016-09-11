#!/usr/bin/make

# @(#) $Id: makefile 13 2008-09-05 05:58:51Z gfis $
# 2016-09-11: regression, jfind
# 2015-08-08: $(GWBASIC) for test data; target populate_lib
# 2015-08-01: Georg Fischer

APPL=basdetok
JAVA=java -cp dist/$(APPL).jar org.teherba.$(APPL).Command
JAVA=java -jar dist/$(APPL).jar
REGR=java -cp dist/$(APPL).jar org.teherba.common.RegressionTester
# REGR=java -Xss512m -Xms1024m -Xmx2048m -cp dist/$(APPL).jar org.teherba.common.RegressionTester
DIFF=diff -y --suppress-common-lines --width=160
DIFF=diff -w -rs -C0
SRC=src/main/java/org/teherba/$(APPL)
TOM=c:/var/lib/tomcat/
TOMC=$(TOM)/webapps/$(APPL)
TESTDIR=test
# the following can be overriden outside for single or subset tests,
# for example make regression TEST=U%
TEST="%"
# for Windows, SUDO should be empty
SUDO=

all: regression
usage:
	java -jar dist/$(APPL).jar
#-------------------------------------------------------------------
# Perform a regression test 
regression: 
	java -cp dist/$(APPL).jar \
			org.teherba.common.RegressionTester $(TESTDIR)/all.tests $(TEST) 2>&1 \
	| tee $(TESTDIR)/regression.log
	grep FAILED $(TESTDIR)/regression.log
#
# Recreate all testcases which failed (i.e. remove xxx.prev.tst)
# Handle with care!
# Failing testcases are turned into "passed" and are manifested by this target!
recreate: recr1 regr2
recr0:
	grep -E '> FAILED' $(TESTDIR)/regression*.log | cut -f 3 -d ' ' | xargs -l -ißß echo rm -v test/ßß.prev.tst
recr1:
	grep -E '> FAILED' $(TESTDIR)/regression*.log | cut -f 3 -d ' ' | xargs -l -ißß rm -v test/ßß.prev.tst
regr2:
	make regression TEST=$(TEST) > x.tmp
regeval:
	grep -iHE "tests (FAILED|passed|recreated)" $(TESTDIR)/*.log
# test whether all defined tests in common.tests have *.prev.tst results and vice versa
check_tests:
	grep -E "^TEST" $(TESTDIR)/all.tests   | cut -b 6-8 | sort | uniq -c > $(TESTDIR)/tests_formal.tmp
	ls -1 $(TESTDIR)/*.prev.tst            | cut -b 6-8 | sort | uniq -c > $(TESTDIR)/tests_actual.tmp
	diff -y --suppress-common-lines --width=32 $(TESTDIR)/tests_formal.tmp $(TESTDIR)/tests_actual.tmp
#---------------------------------------------------
jfind:
	find src -iname "*.java" | xargs -l grep -H $(JF)
rmbak:
	find src -iname "*.bak"  | xargs -l rm -v
#---------------------------------------------------
tgz:
	tar czvf $(APPL)_`/bin/date +%Y%m%d`.tgz src test etc web *.wsd* *.xml makefile
#-----------------------------------------------------------------------------------
caccia:
	$(JAVA) -m20 test/m20/caccia > test/m20/caccia.basdetok
	diff    -w test/m20/caccia.bas test/m20/caccia.basdetok | tee x.tmp
othello:
	$(JAVA) -m20 test/m20/othello.bas > test/m20/othello.basdetok
m20_extract:
	# after 0x45 blocks
	dd bs=512 count=2 skip=69 if=$(GWBASIC)/basic_abs of=etc/m20_basic.dd
	# +11b points to the "N" of "(A)ND"
	perl etc/extract_tokens.pl +11b etc/m20_basic.dd | tee etc/m20_tokens.tmp
pc_extract:
	# dump < $(GWBASIC)/GWBASIC.EXE | tee pc-gwbasic.dump.tmp
	# after 0x2b blocks
	dd bs=512 count=2 skip=43 if=$(GWBASIC)/GWBASIC.EXE of=etc/pc_basic.dd
	# +11b points to the "U" of "(A)UTO"
	perl etc/extract_tokens.pl +d3 etc/pc_basic.dd | tee etc/pc_tokens.tmp
msx_extract:
	# 3a72
	dd bs=512 count=2 skip=29 if=$(GWBASIC)/msx/msx.rom of=etc/msx_basic.dd
	# +72 points to the "U" of "(A)UTO"
	perl etc/extract_tokens.pl +72 etc/msx_basic.dd | tee etc/msx_tokens.tmp
sorcerer_extract:
	# 00f6
	dd bs=512 count=2 skip=0 if=$(GWBASIC)/sorcerer/basic1.01.rom of=etc/sorcerer_basic.dd
	# +f6 points to "END"
	perl etc/extract_tokens.pl +f6 -tn 80 etc/sorcerer_basic.dd | tee etc/sorcerer_tokens.tmp
dragon_extract:
	# 0033
	dd bs=512 count=2 skip=0 if=$(GWBASIC)/dragon/dragon.rom of=etc/dragon_basic.dd
	# +33 points to "FORGOREM'ELSE..."
	perl etc/extract_tokens.pl +33 -tn 80 etc/dragon_basic.dd | tee etc/dragon_tokens.tmp

