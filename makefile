#!/usr/bin/make

# @(#) $Id: makefile 13 2008-09-05 05:58:51Z gfis $
# 2015-08-08: $(GWBASIC) for test data; target populate_lib

APPL=basdetok
JAVA=java -cp dist/$(APPL).jar org.teherba.$(APPL).Command
GWBASIC=~/work/floppy/gw-basic

all: javadoc deploy zip

populate_lib:
	wget http://mirror.cc.columbia.edu/pub/software/apache/logging/log4j/1.2.17/log4j-1.2.17.zip
	unzip -u -j -d . log4j-1.2.17.zip apache-log4j-*/log4j-*.jar
	rm -f lib/log4j*.jar
	mv -v log4j*.jar lib
	rm -f log4j*.zip
run:
javadoc:
	ant javadoc
deploy:
	ant deploy
zip:
	rm -f $(APPL).zip
	find . | grep -v "test/" | zip -@ $(APPL).zip
#-----------
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

