3 REM "E.PETRELLI A.VERCESI 15/12/83
5 GOSUB 20000:CLS:DIM DIR$(4):DIR$(1)="Nord":DIR$(2)="Est":DIR$(3)="Sud":DIR$(4)="Ovest"
7 DIM E(10,10),B(10,10),M(11),M$(11),N(11),T$(11),P(11),G$(3),AR$(11,3),MP$(11)
9 DIM C(15),D(15),F$(7):F$(0)="A":F$(1)="-":F$(2)="M":F$(3)="L":F$(5)=" ":F$(7)="*":E$="."+CHR$(13)+CHR$(10)+" "
10 FOR I=1 TO 11:FOR J=1 TO 3:READ AR$(I,J):NEXT:READ M$(I),MP$(I),T$(I),M(I),P(I):NEXT
11 DEF FNV$(X)=MID$(STR$(X),2):DEF FNP$(X,Y)=STRING$(X-LEN(FNV$(Y)),".")
13 ZI=0:V=INT(RND*3)
15 C=INT(RND*1501+500):S=INT(RND*6):R=INT(RND*4)
17 G$(1)="Magia del Sonno":G$(2)="Magia del Sortilegio":G$(3)="Magia dell'Invisibilita'"
18 PRINT:PRINT:PRINT:PRINT"Vuoi usare valori particolari di Forza e Magia (s/n) ? ";:X$="":WHILE X$="":I=(I+1)MOD 1000:X$=INKEY$:WEND:PRINT X$:RANDOMIZE I:IF X$="s" THEN 1630
20 D=C:V1=V:S1=S:R1=R:GOSUB 10000:PRINT:PRINT:PRINT"Prego attendere..."
25 FOR I=1 TO 10:FOR J=1 TO 10
27 T=INT(RND*10):IF T<>1 OR CS=15 THEN T=0
30 H=INT(RND*2):W=INT(RND*10):P=INT(RND*51)
35 A(I,J)=10000*T+100*P+10*W+H
37 IF T=1 THEN CS=CS+1:C(CS)=I:D(CS)=J
40 NEXT:NEXT:T=0
45 X1=INT(RND*8)+2:Y1=INT(RND*8)+2
50 X=INT(RND*10)+1:Y=INT(RND*10)+1
55 IF X1<1 OR X1>10 OR Y1<1 OR Y1>10 THEN 1000
56 FOR I=1 TO 10:FOR J=1 TO 10:B(I,J)=0:NEXT:NEXT
57 CA=INT(A(X1,Y1)/10000):P=INT((A(X1,Y1)-(10000*CA))/100)
60 W=INT((A(X1,Y1)-10000*CA-100*P)/10)
65 H=A(X1,Y1)-10000*CA-100*P-10*W:I=0:J=0
67 IF CA=1 THEN I=INT(RND*10+1):J=INT(RND*10+1):B(I,J)=7
70 IF CA=1 AND I=X AND Y=J THEN B(I,J)=0:GOTO 67
75 IF H=1 THEN I=INT(RND*10+1):J=INT(RND*9+1)
85 IF H=1 AND B(I,J)<>0 THEN 75
87 IF H=1 THEN B(I,J)=3
90 B(X,Y)=5:IF W=0 THEN 115
95 FOR I=1 TO W
100 J=INT(RND*10)+1:K=INT(RND*10)+1
105 IF B(J,K)<>0 THEN 100
110 B(J,K)=2:NEXT
115 IF P=0 THEN 140
120 FOR I=1 TO P
125 J=INT(RND*10)+1:K=INT(RND*10)+1
130 IF B(J,K)<>0 THEN 125
135 B(J,K)=1:NEXT
140 IF ZI=0 THEN CLS:GOSUB 3705:V$="":GOSUB 3000:ZI=-1
150 GOSUB 3500
160 GOSUB 3600
210 IF T=1 THEN T=0:RETURN
213 IF T=2 THEN 515
215 GOSUB 3000:I=INT(RND*5):IF I=2 THEN GOSUB 1500:FOR A=1 TO 4000:NEXT
220 IF I=1 AND T<>9 THEN V$=V$+"Qui non c'e' nulla"+E$:GOTO 515
223 IF I=1 AND T=9 THEN 513
225 I=INT(RND*16+1):N=I:IF I=12 THEN 840
235 IF I=13 THEN 870
237 IF I=14 THEN 900
240 IF I>14 THEN J=100:V$=V$+"Trovi":GOTO 267
245 J=INT(RND*100/M(I)):N1=J:IF J=0 THEN J=1:N1=J
250 V$=V$+"Trovi "
255 IF J=1 THEN V$=V$+AR$(I,1)+" "
258 IF J=1 THEN V$=V$+M$(I):M$=AR$(I,2)+" "+M$(I)
260 IF J<>1 THEN V$=V$+STR$(J)+" "+MP$(I):M$=AR$(I,3)+" "+MP$(I)
262 V$=V$+" (Tot"+STR$(M(I)*J)+") di guardia a "
265 M=M(I)*J
267 I=INT(RND*14)+1
270 IF I>11 AND J=100 THEN 215
271 IF I<12 AND J=100 THEN V$=V$+"che nessuno e' di guardia a "
272 IF I>11 THEN 975
273 IF I>11 THEN V$=V$+"nulla"+E$:P=0:GOTO 277
275 V$=V$+T$(I)+E$:P=P(I)
277 IF M$=M$(11) AND M1=7 THEN 835
279 IF J=100 THEN V$=V$+"Conquisti il tesoro senza colpo ferire"+E$:GOTO 500
280 GOSUB 3400:PRINT"Vuoi:  (1) batterti,"TAB(8)"(2) fuggire,"TAB(8)"(3) tentare una corruzione, o"TAB(8)"(4) tentare una magia ?     -->";:INPUT"",K
285 IF K<1 OR K>4 THEN 280
290 ON K GOTO 295,350,435,670
295 PRINT:INPUT"Quanti punti di combattimento";K:IF K=0 THEN PRINT CHR$(7);:CURSOR(1,POS(1)-1):GOTO 295
300 PRINT:PRINT:IF K>C THEN PRINT CHR$(7)"Hai solo"C"punti !";:CURSOR(1,POS(1)-3):GOTO 295
305 GOSUB 3000:I=INT(RND*1001):L=2:C=C-K:K=K-.01*Q
310 FOR H=1000 TO 0 STEP -50
315 IF L*M<K AND H>=1 THEN 490
320 L=L-.1:NEXT
325 PRINT"Sei stato ucciso da "M$".":PRINT"Perdi tutto !"
335 PRINT:PRINT"Vuoi riprovare (s/n) ? ";:X$=INPUT$(1):PRINT
340 IF X$="s" THEN RUN
345 CLS:CURSOR(8,1):PRINT"Buona fortuna per la prossima volta....":END
350 I=INT(RND*12):IF I=11 THEN 325
360 IF I*10>=M THEN 375 ELSE 480
375 A=X:B=Y:K=0:T=0:C=C-INT((RND*21)+.001*Q)-5
380 X=A+INT(RND*3)-1:Y=B+INT(RND*3)-1
385 IF X=A AND Y=B THEN 380
390 D1=D1+.1:IF X>10 THEN X=1:X1=X1+1:K=1
395 IF Y>10 THEN Y=1:Y1=Y1+1:K=1
400 IF X<1 THEN X=10:X1=X1-1:K=1
405 IF Y<1 THEN Y=10:Y1=Y1-1:K=1
410 IF B(X,Y)>1 AND K=0 THEN 380
415 B(A,B)=INT(RND*3):B(X,Y)=5:IF I<>11 THEN Z=Z+1
425 IF K=1 THEN 55
430 GOTO 215
435 PRINT:INPUT"Quanto vuoi pagare ? ",K:PRINT:PRINT
440 IF K>Q THEN PRINT CHR$(7)"Hai solo un tesoro di"Q"punti !";:CURSOR(1,POS(1)-3):GOTO 435
445 I=INT(RND*22):L=0:IF I=21 OR(I>15 AND K<2) THEN 325
455 J=(P+(M*.1))*N1:IF K<2 THEN 475
460 FOR H=0 TO 20:IF K<=J*L AND I>=H THEN 475
470 L=L+.1:NEXT:GOTO 485
475 PRINT"La corruzione non e' riuscita."
480 PRINT"Devi batterti.":GOTO 295
485 P=0:Q=Q-K:BR=BR+1:T=0:PRINT"La corruzione e' riuscita !":GOTO 505
490 N(N)=N(N)+N1
495 PRINT"Hai battuto "M$"!"
500 IF N<12 THEN I=INT(RND*7):IF I=3 THEN 940
501 IF J=100 THEN I=INT(RND*5):IF I=3 THEN 965
502 Q=Q+P:IF P=25 THEN 770
504 IF T>5 AND T<>9 THEN Q=Q-P:GOTO 985
505 V$=V$+"Ora hai un tesoro di"+STR$(Q)+" punti"+E$
510 IF P=200 THEN 800
513 IF T=9 THEN GOSUB 1200
515 FOR N=1 TO 2000:NEXT:GOSUB 3600: GOSUB 3400:PRINT
516 PRINT"Direzione (usare le frecce) ? ";:X$=INPUT$(1)
517 IF INSTR("8624",X$)=0 THEN PRINT CHR$(7);:CURSOR(1,POS(1)):GOTO 516 ELSE PRINT DIR$(INSTR("8624",X$))
520 INPUT"Distanza                    ";K:IF K<0 THEN PRINT CHR$(7);:CURSOR(1,POS(1)-1):GOTO 520 ELSE K=INT(K)
521 GOTO 1100
523 A1=X1:B1=Y1:A=X:B=Y:C=C-INT(7.5*K*RND)
525 IF X$="8" THEN Y=Y+K
530 IF X$="2" THEN Y=Y-K
535 IF X$="6" THEN X=X+K
540 IF X$="4" THEN X=X-K
542 DM%=0
545 WHILE X>10:X=X-10:X1=X1+1:DM%=-1:WEND
550 WHILE X<1 :X=X+10:X1=X1-1:DM%=-1:WEND
555 WHILE Y>10:Y=Y-10:Y1=Y1+1:DM%=-1:WEND
560 WHILE Y<1 :Y=Y+10:Y1=Y1-1:DM%=-1:WEND
561 IF B(X,Y)=1 THEN C=C-5
563 IF B(X,Y)=0 THEN C=C-10
565 IF C<=0 THEN PRINT"Hai esaurito la forza.";:GOTO 330
570 IF X1<>A1 OR Y1<>B1 THEN 55
573 IF B(X,Y)=7 THEN T=9
575 IF B(X,Y)=2 THEN 590
580 IF B(X,Y)=3 THEN 600
585 B(A,B)=INT(RND*3):B(X,Y)=5:GOTO 150
590 PRINT"Non puoi passare il muro !"
595 C=C-INT(RND*Q*.005)-25:X=A:Y=B:GOTO 515
600 Y=Y-1:B(A,B)=INT(RND*3):B(X,Y)=5:T=1:V=V1:R=R1:S=S1:IF C<D THEN C=D
603 GOSUB 150
605 V$=V$+"Hai raggiunto una Locanda e se ne hai perse, riacquisti forza e magie"+E$
610 I=INT(RND*Q*.05):IF I<5 AND Q>5 THEN I=5
615 IF I<5 AND Q<=5 THEN I=0
620 V$=V$+"Hai pagato"+STR$(I)+" punti del tesoro per la sosta alla Locanda"+E$:Q=Q-I
625 V$=V$+"Ora hai un tesoro di"+STR$(Q)+" punti"+E$
630 I=INT(RND*3):IF I=2 THEN 515
633 IF I=1 THEN GOSUB 1300:GOTO 515
635 I=INT(RND*4+1)
640 V$=V$+"L'oste ti ha detto che la foresta termina a meno di"
645 ON I GOTO 650,655,660,665
650 V$=V$+STR$(Y1*100)+" metri verso Sud"+E$:GOTO 515
655 V$=V$+STR$((11-Y1)*100)+" metri verso Nord"+E$:GOTO 515
660 V$=V$+STR$(X1*100)+" metri verso Ovest"+E$:GOTO 515
665 V$=V$+STR$((11-X1)*100)+" metri verso Est"+E$:GOTO 515
670 IF T>5 AND T<>9 THEN V$=V$+"Non puoi conquistare una Magia con una Magia"+E$:GOTO 280
671 IF S+V+R=0 THEN V$=V$+"Non hai alcuna Magia"+E$:GOTO 280
673 PRINT:PRINT"Quale Magia ?"TAB(8)"(1) del Sonno,"TAB(8)"(2) del Sortilegio, o"TAB(8)"(3) dell' Invisibilita'      --> ";
675 INPUT"",K:IF K<1 OR K>3 THEN PRINT CHR$(7);:GOTO 670
680 GOSUB 3000:ON K GOTO 685,720,745
685 IF S=0 THEN PRINT"Non hai Magie del Sonno !";:GOTO 480
690 IF N=4 THEN PRINT"Gli Zombi non possono dormire !":S=S-1:GOTO 480
695 I=INT(RND*10):S=S-1
700 IF I<3 THEN PRINT"La Magia non e' riuscita.":GOTO 480
705 IF I<8 THEN PRINT"Hai conquistato il tesoro.":GOTO 500
710 PRINT"Risveglio anticipato de"M$" !"
713 P=INT(RND*P):Q=Q+P
715 V$=V$+"Procedi con"+STR$(P)+" punti di tesoro conquistati"+E$:GOTO 515
720 IF R=0 THEN PRINT"Non hai Magie del Sortilegio.";:GOTO 480
725 I=INT(RND*10):R=R-1
730 IF M>50 AND I<2 THEN PRINT"Il Sortilegio non ha funzionato.":GOTO 480
735 IF I=3 THEN V$=V$+"Il Sortilegio e' svanito troppo presto"+E$:GOTO 713
740 I=3:GOTO 705
745 IF V=0 THEN PRINT"Non hai Magie dell'Invisibilita'.";:GOTO 480
750 I=INT(RND*10):V=V-1
755 IF M>50 AND I>8 THEN V$=V$+"Sei stato solo fiutato da"+M$+E$:GOTO 713
760 IF M<60 AND I=0 THEN V$=V$+"L'Invisibilita' e' svanita troppo presto"+E$:GOTO 713
765 GOTO 740
770 I=INT(RND*2)+1:ON I GOTO 780,790
780 C=C+C:V$=V$+"Hai conquistato una Spada Incantata. La tua forza raddoppia"+E$:GOTO 505
790 V$=V$+"La Spada era solo una Spada normale. Rimani con la stessa forza"+E$:GOTO 505
800 J=INT(RND*10):I=INT(RND*10)
805 IF J=7 AND M1<>7 THEN M1=7:GOTO 820
810 IF I=1 THEN 830
815 GOTO 513
820 V$=V$+"Nella Cassa c'e' uno Specchio Magico. Ti proteggera' contro ogni "+M$(11)+" che incontrerai"+E$:M1=7:GOTO 515
830 V$=V$+"La Cassa del Tesoro era solo una trappola ! Ti ha ucciso aprendola"+E$:GOSUB 3400:GOTO 335
835 V$=V$+"Il tuo Specchio Magico ha ucciso "+M$+E$:N(11)=N(11)+1:M=0:GOTO 500
840 V$=V$+"Un Pipistrello Gigante ti ha afferrato e trasportato in un altro punto"+E$
845 A=X:B=Y:T=0:D1=D1+.1
850 X=INT(RND*10)+1:Y=INT(RND*10)+1:IF B(X,Y)>1 THEN 850
860 B(A,B)=INT(RND*3):B(X,Y)=5:GOSUB 3400:GOTO 150
870 V$=V$+"Sei caduto in un pozzo.  ":I=INT(RND*21+.001*Q):C=C-I
875 IF C<0 THEN V$=V$+"Muori cercando di uscirne"+E$:GOSUB 3400:GOTO 330
880 V$=V$+"Hai dovuto usare"+STR$(I)+" punti di forza per uscirne"+E$:I=11
882 GOSUB 3400
885 GOTO 516
900 J=0:FOR I=1 TO 11:J=J+N(I):NEXT:IF J<11 THEN 215
910 PRINT"Un'Aquila Gigante ti ha portato in salvo fuori dalla Foresta.";
915 GOTO 1000
940 I=INT(RND*11)+1:M=M(I):M$=M$(I):N=I
945 V$=V$+AR$(I,1)+" "+M$+" ("+MID$(STR$(M(I)),2)+") ha sentito il rumore della lotta e sopraggiunge"+E$:M$=AR$(I,2)+" "+M$
947 IF I=11 AND M1=7 THEN 835
950 GOSUB 3400:PRINT"Vuoi :"TAB(8)"(1) batterti,"TAB(8)"(2) tentare la fuga, o"TAB(8)"(3) provare una Magia  ?  -->";:INPUT"",K
955 IF K<1 OR K>3 THEN PRINT CHR$(7):GOTO 950
960 ON K GOTO 295,350,670
965 I=INT(RND*11)+1:M=M(I):M$=M$(I):N=I
970 V$=V$+"Sopraggiunge "+AR$(I,1)+" "+M$+" ("+MID$(STR$(M(I)),2)+")"+E$:M$=AR$(I,2)+" "+M$:GOTO 947
975 IF I<>14 THEN 273
980 I=INT(RND*3+1):T=I+5:V$=V$+"Una "+G$(I):P=INT(RND*11):GOTO 277
985 I=INT(RND*10)
986 IF I=5 THEN V$=V$+"Non sei riuscito ad afferrarla, cosi' non conquisti la "+G$(T-5)+E$:GOTO 515
988 IF T=6 THEN S=S+1:S1=S1+1
989 IF T=7 THEN R=R+1:R1=R1+1
990 IF T=8 THEN V=V+1:V1=V1+1
995 V$=V$+"Hai conquistato la "+G$(T-5)+E$:T=0:IF S1/5+R1/3+V1/2>6 THEN GOSUB 1665
997 GOTO 515
1000 FOR A=1 TO 4000:NEXT:CLOSE WINDOW:CLS
1003 PRINT"   Sei in salvo fuori dalla Foresta !"
1004 PRINT"    Ecco il quadro dei risultati:"
1007 PRINT:PRINT:COLOR 0,1:PRINT" MOSTRI UCCISI ";:CURSOR(30,POS(1)):PRINT" MOSTRI UCCISI ";:COLOR 1,0:PRINT
1010 FOR I=1 TO 5:PRINT M$(I) TAB(15) N(I) TAB(30) M$(I+5) TAB(45) N(I+5)
1015 NEXT:PRINT TAB(30) M$(11) TAB(45) N(11)
1020 PRINT:COLOR 0,1:PRINT" CORROTTI : ";:COLOR 1,0:PRINT TAB(15) BR TAB(30);:COLOR 0,1:PRINT" SFUGGITI : ";:COLOR 1,0:PRINT TAB(45) Z
1030 PRINT:PRINT"  Tesoro Totale :  " Q:PRINT
1035 IF Q1<>0 THEN GOSUB 1650
1040 PRINT"  COMPLIMENTI ";:IF Q1<>0 AND Q1>Q THEN PRINT"...COMUNQUE";:PRINT"  !":PRINT
1043 PRINT:X$=""
1045 IF D1<30 THEN PRINT"Vuoi tornare nella Foresta (s/n) ? ";:X$=INPUT$(1):PRINT X$
1050 S=S1:V=V1:R=R1:C=D:IF X$<>"s" THEN 1600
1055 CLS:GOSUB 10000:ZI=0:GOTO 45
1100 D1=D1+K/10:IF D1<30 THEN 523
1110 PRINT"Il tempo e' trascorso: sono passati 30 giorni."
1115 FOR I=1 TO 2000:NEXT:GOTO 910
1200 T=0
1203 V$=V$+"Hai raggiunto un Castello Incantato"+E$
1205 I=INT(RND*21)*100:J=INT(RND*9):A(X1,Y1)=A(X1,Y1)-10000
1210 V$=V$+"Qui trovi un Tesoro di"+STR$(I)+" punti"+E$:Q=Q+I
1215 IF J<>7 OR M=1 THEN 1225
1220 V$=V$+"Trovi anche uno Specchio Magico che uccidera' ogni "+M$(11)+" che incontrerai"+E$:M1=7
1225 J=INT(RND*20):IF J=2 THEN C=2*C
1230 IF J=2 THEN V$=V$+"Trovi anche una Spada Incantata che raddoppia la tua forza"+E$
1240 FOR I=1 TO CS-1:IF C(I)<>X1 THEN 1245
1241 FOR J=1 TO CS-1:C(J)=C(J+1):D(J)=D(J+1):NEXT
1245 NEXT:CS=CS-1:IF CS=0 THEN V$=V$+"Questo era l'ultimo Castello esistente"+E$
1250 GOSUB 3600:GOTO 3400
1300 IF CS=0 THEN RETURN
1301 I=INT(RND*CS)+1
1302 V$=V$+"L'oste ti ha parlato della leggenda di un castello "
1303 IF C(I)=X1 AND D(I)=Y1 THEN V$=V$+"molto vicino"+E$:GOTO 3400
1304 J=X1-C(I):I=Y1-D(I)
1305 IF ABS(I)=ABS(J) THEN V$=V$+"direttamente a ":GOTO 1307
1306 V$=V$+"piu' o meno verso "
1307 IF I<0 THEN V$=V$+"Nord"
1310 IF I>0 THEN V$=V$+"Sud"
1315 IF J<0 THEN V$=V$+"Est"
1320 IF J>0 THEN V$=V$+"Ovest"
1325 V$=V$+E$+RIGHT$(E$,2):GOTO 3400
1500 I=INT(RND*11+1)
1501 LL=LEN(V$):ON I GOSUB 1510,1520,1515,1530,1540,1550,1560,1570,1580,1590,1595,:IF LEN(V$)>LL THEN V$=V$+E$
1502 IF CC=0 THEN GOSUB 3400:GOTO 3600 ELSE CC=0:GOTO 1303
1510 V$=V$+"Un Vortice Temporale ti ha fatto perdere 7 giorni":D1=D1+7:RETURN
1515 I=INT(RND*10+1):J=D1:D1=D1-I:IF D1<.1 THEN D1=.1:I=J-D1
1517 V$=V$+"Un Vortice Temporale ti ha fatto guadagnare"+STR$(I)+" giorni":RETURN
1520 IF C>=D THEN RETURN
1523 V$=V$+"Un Elfo ti ha preparato la Pozione Magica che restituisce la forza":C=D:RETURN
1530 IF V+R+S=V1+R1+S1 THEN RETURN
1533 V$=V$+"Un Mago ti ha dato una Pozione che ti ha restituito le Magie":V=V1:R=R1:S=S1:RETURN
1540 IF Q<2 THEN RETURN
1543 V$=V$+"Caduta nelle Sabbie Mobili: perdi meta' del Tesoro":Q=INT(Q/2):RETURN
1550 V$=V$+"Un fitto sottobosco ha richiesto meta' della forza per essere superato":C=INT(C/2):RETURN
1560 I=INT(RND*50+1):V$=V$+"Hai trovato"+STR$(I)+" Monete d'oro":Q=Q+I:RETURN
1570 IF M<>7 THEN RETURN
1573 V$=V$+"Hai inciampato nelle radici ed hai perduto lo Specchio Magico":M1=0:RETURN
1580 V$=V$+"Un'eremita ti ha detto che ci sono"+STR$(CS)+" Castelli Incantati":RETURN
1590 IF V+S+R=0 THEN RETURN
1591 V$=V$+"Ti trovi in una zona in cui le Magie non funzionano. "
1593 V$=V$+"Perdi tutte le Magie":V=0:S=0:R=0:RETURN
1595 IF CS=0 THEN RETURN
1596 V$=V$+"Un cacciatore ti ha parlato della leggenda di un castello ":LL=LEN(V$):I=INT(RND*CS)+1:CC=-1:RETURN
1600 FOR I=1 TO 2000:NEXT:PRINT
1605 PRINT"Vuoi una nuova Foresta con la stessa forza e le stesse Magie (s/n) ? ";:X$=INPUT$(1):PRINT X$:IF X$="s" THEN 1625
1615 PRINT"Vuoi una nuova Foresta con forza e Magie nuove ? ";:X$=INPUT$(1):IF X$="s" THEN RUN
1618 PRINT"Prevedi di usare forza e Magie attuali in un'altra caccia ? ";:X$=INPUT$(1):PRINT X$:IF X$="s" THEN GOSUB 1700:PRINT:PRINT"Inoltre il tesoro totale era di"Q"punti."
1622 IF Q>Q1 THEN Q1=Q
1623 IF Q1<>Q THEN PRINT"Il massimo Tesoro che hai avuto e' stato di"Q1"punti.":PRINT:PRINT"Arrivederci alla prossima caccia...":END
1625 BR=0:Z=0:D1=0:FOR I=1 TO 11:N(I)=0:NEXT:IF Q1<Q THEN Q1=Q
1627 Q=0:ZI=0:GOTO 20
1630 INPUT"Forza di combattimento (500-2000) ";C
1635 IF C<500 OR C>2000 THEN PRINT CHR$(7);:GOTO 1630
1640 INPUT"Magie del Sonno ";S:INPUT"Magie del Sortilegio ";R:INPUT"Magie dell'Invisibilita' ";V
1645 INPUT"Precedente Tesoro massimo totale ";Q1:GOTO 20
1650 IF Q1<Q THEN PRINT"Hai vinto un Tesoro maggiore del precedente."
1653 IF Q1>Q THEN PRINT"Stavolta non hai guadagnato in Tesoro."
1660 RETURN
1665 PRINT"Hai molte Magie, vuoi convertirle in punti di forza (s/n) ? ":X$=INPUT$(1):IF X$<>"s" THEN RETURN
1675 S1=S1-5:R1=R1-3:V1=V1-2:IF S1<=0 THEN S1=1
1680 IF R1<=0 THEN R1=1
1685 IF V1<=0 THEN V1=1
1690 S=S1:R=R1:V=V1:C=C+100:D=D+100:V$=V$+"La tua forza aumenta di 100 punti in modo permanente"+E$:RETURN
2000 DATA uno,lo,gli,Spettro,Spettri,10 Cucchiai d'argento (10 punti),5,10,un,il,i,Minotauro,Minotauri
2005 DATA una Spada Incantata (25 punti),10,25
2010 DATA un,il,i,Ciclope,Ciclopi,50 Monete d'argento (50 punti),20,50,uno,lo,gli,Zombi,Zombi
2015 DATA 100 Pezzi d'oro (100 punti),30,100,un,il,i,Gigante,Giganti
2020 DATA un Bracciale di smeraldi (50 punti),40,50,"un'","l'",le,Arpia,Arpie
2025 DATA la Cassa del Tesoro (200 punti),50,200,un,il,i,Grifone,Grifoni
2030 DATA una Collana di perle (50 punti),60,50,una,la,le,Chimera,Chimere
2035 DATA una Spada ingioiellata (30 punti),70,30,un,il,i,Drago,Draghi
2040 DATA una Giara di Rubini (75 punti),80,75,"un'","l'",le,Idra,Idre
2045 DATA uno Scrigno di Gioielli (100 punti),90,100,un,il,i,Basilisco,Basilischi
2050 DATA un Calice d'Oro (50 punti),100,50
3000 WINDOW %2:PRINT:RETURN
3400 WINDOW %2:PRINT:LR%=43:PI%=1:WHILE PI%<LEN(V$)
3410 PX%=PI%
3420 PB%=INSTR(PX%,V$," "):PF%=INSTR(PX%,V$,CHR$(10)):IF PF%<PB%AND PF%>0 THEN PX%=PF%+1:GOTO 3430
3425 IF PB%-PI%<LR% AND PB%>0 THEN PX%=PB%+1:GOTO 3420 ELSE IF PB%=0 THEN PX%=LEN(V$)+1
3430 PRINT MID$(V$,PI%,PX%-PI%):PI%=PX%:WEND:V$="":RETURN
3500 WINDOW%1:BQ%=15:BO%=5:LINE(BO%,BQ%)-STEP(199,159),0,BF:FOR I=1 TO 10:YD%=(I-1)*16+BQ%:FOR J=1 TO 10:XD%=(J-1)*20+BO%
3502 GOSUB 3520:ON B(J,I)+1 GOSUB 3510,3520,3530,3540,,3550,,3560
3504 NEXT:NEXT:COLOR 1,0:LINE(BO%-5,BQ%-5)-STEP(209,169),2,B:LINE(BO%-1,BQ%-1)-STEP(201,161),2,B
3506 FOR W=BQ%-3 TO BQ%+161 STEP 164:FOR N=BO% TO BO%+199 STEP 40:LINE(N,W)-STEP(19,1),,BF:NEXT:NEXT:FOR W=BO%-3 TO BO%+201 STEP 204:FOR N=BQ% TO BQ%+159 STEP 32:LINE(W,N)-STEP(1,15),,BF:NEXT:NEXT
3509 RETURN
3510 LINE(XD%+9,YD%+1)-STEP(0,4):LINE STEP(0,-1):LINE STEP(-6,0):LINE STEP(8,10):LINE STEP(7,-10):LINE STEP(-6,0):LINE STEP(0,-4):LINE STEP(-3,0):RETURN
3520 LINE(XD%,YD%)-STEP(19,15),,BF,PRESET:RETURN
3530 COLOR 3:LINE(XD%,YD%)-STEP(19,15),,B:FOR W%=3 TO 11 STEP 4:LINE(XD%,YD%+W%)-STEP(19,0):NEXT:FOR W%=3 TO 19 STEP 7:LINE(XD%+W%,YD%)-STEP(0,3):LINE(XD%+W%,YD%+8)-STEP(0,3):NEXT:FOR W%=6 TO 15 STEP 8:LINE(XD%+W%,YD%+4)-STEP(0,3)
3532 LINE(XD%+W%,YD%+12)-STEP(0,3):NEXT:COLOR 1,0:RETURN
3540 COLOR 0,3:GOSUB 3520:COLOR 0,1:LINE(XD%+7,YD%+1)-STEP(-4,4):LINE STEP(0,5):LINE STEP(2,2):LINE STEP(0,-1):LINE STEP(1,-1):LINE STEP(2,0):LINE STEP(1,1):LINE STEP(1,0):LINE STEP(1,-1):LINE STEP(2,0):LINE STEP(1,1)
3541 LINE STEP(0,1):LINE STEP(2,-2):LINE STEP(0,-5):LINE STEP(-4,-4):LINE STEP(-4,0)
3542 LINE(XD%+8,YD%+4)-STEP(1,4),,BF:LINE(XD%+10,YD%+4)-STEP(1,1),,BF:COLOR 1,0:RETURN
3550 LINE(XD%,YD%)-STEP(19,15),2,BF:LINE(XD%+9,YD%+1)-STEP(-4,4),0:LINE STEP(0,7),0:LINE STEP(2,2),0:LINE STEP(5,0),0:LINE STEP(2,-2),0:LINE STEP(0,-7),0:LINE STEP(-4,-4),0
3552 FOR W%=5 TO 14 STEP 9:LINE(XD%+W%,YD%+14)-STEP(0,0),0:NEXT:N%=0:FOR W%=7 TO 11 STEP 2:FOR W1%=6 TO 10 STEP 2:LINE(XD%+W%,YD%+W1%)-STEP(1,1),(N%MOD 2)*2,BF:N%=N%+1:NEXT:NEXT:RETURN
3560 LINE(XD%+2,YD%+1)-STEP(15,9),2,BF:LINE(XD%+1,YD%+9)-STEP(17,1),2,BF:FOR W%=1 TO 17 STEP 4:LINE(XD%+W%,YD%+11)-STEP(1,1),2,BF:NEXT:LINE(XD%+9,YD%+13)-STEP(1,1),2,BF:RETURN
3600 WINDOW%1:PH%=3
3610 LINE(0,-70)-(210,5),0,BF:FOR I=1 TO 7:CURSOR(PH%,I+18):ON I GOSUB 3620,3630,3640,3650,3670,3680,3695:NEXT:LINE(0,-70)-(210,5),,BF,XOR:RETURN
3620 PRINT"_________ Situazione _________";:RETURN
3630 PRINT"- Forza";FNP$(23,C);FNV$(C);:RETURN
3640 PRINT"- Tesoro";FNP$(22,Q);FNV$(Q);:RETURN
3650 PRINT"- Magie : - Sonno";FNP$(13,S);FNV$(S);:RETURN
3670 PRINT SPC(10);"- Sortilegi";FNP$(9,R);FNV$(R);:RETURN
3680 PRINT SPC(10);"- Invisibilita'";FNP$(5,V);FNV$(V);:RETURN
3695 PRINT"    Giorni trascorsi";TAB(21+PH%);:PRINT USING"##.#";D1;:RETURN
3705 RETURN
10000 CLOSE WINDOW:W%=WINDOW(0,0,10,6):LW%=36:HW%=256:W%=WINDOW(3,LW%):SCALE %1,0,LW%*6-1,-70,HW%-71:WINDOW%2:CURSOR(1,5):WINDOW%1:RETURN
20000 CLS:X=WINDOW(0,0,10,6):PRINT TAB(5);:COLOR 0,1:PRINT"* * * ALLA CONQUISTA DEL TESORO NELLA GRANDE FORESTA INCANTATA * * *":COLOR 1,0
20010 PRINT
20020 PRINT"Nel corso del gioco ti troverai nella Grande Foresta Incantata dove sono"
20030 PRINT"nascosti Mostri e Tesori.   La Grande Foresta Incantata e' costituita da 100"
20040 PRINT"quadrati di 100 metri di lato ciascuno.  Sullo schermo vedrai soltanto il"
20050 PRINT"quadrato nel quale ti trovi senza sapere quale dei 100 esso sia.":PRINT
20060 PRINT"Sparsi nella Foresta sono nascosti tesori di vario valore, ma i tesori maggiori si trovano nei Leggendari Castelli Abbandonati che possono essere in vario      numero dislocati nella Foresta.":PRINT
20070 PRINT"Tu dovrai conquistare il massimo Tesoro possibile, cercando di battere i Mostri che li custodiscono. Partirai con una certa Forza e con alcune Magie che ti"
20075 PRINT"permetteranno di superare le prime difficolta'. poi potrai sostare alle locande dove";
20080 PRINT" potrai riconquistare Forza e Magie perdute.":PRINT
20090 PRINT"Battendoti con i Mostri se userai una forza pari alla loro avrai il 50% delle   probabilita' di vincere, ma via via che il tuo tesoro crescera' i Mostri        saranno sempre piu' aggressivi e dovrai usare piu' Forza per essere certo di"
20100 PRINT"batterli.":PRINT
20110 PRINT"Ora non posso dirti di piu' ! Prova tu stesso l'emozione della Caccia nella     GRANDE FORESTA INCANTATA !":PRINT
20120 COLOR 0,1:PRINT"  ---- LA FORZA SIA CON TE ! ----  ";:COLOR 1,0:PRINT"      (qualsiasi tasto per iniziare)";
20130 R$=INPUT$(1):RETURN
