#!/usr/bin/perl

# determine the floating point representation in BASIC for Olivetti M20
# @(#) $Id$
# 2012-09-29, Georg Fischer
#
# Activation:
#   perl m20_floats.pl

use strict;

	my $bits;
	my $float;
	my $exponent;
	my $mantissa;
	my $sign;
	my @pow2;
	my $value = 1;
	for my $exp (0 .. 48) {
		$pow2[$exp] = $value;
		$value *= 2;
	} # for $exp
	
	while(<DATA>) {
		s{\s+\Z}{}; # chompr
		$_ = substr($_, 3);
		($bits, $float) = split;
		my $orig_bits = substr($bits, 0, 35);
		$bits =~ s{\D}{}g;
		$exponent = oct("0b"  . substr($bits, 1, 7));
		$exponent = substr($bits, 1, 1) eq "0" ? $exponent : 0x80 - $exponent;
		$sign     = substr($bits, 8, 1);	
		$mantissa = oct("0b1" . substr($bits, 9));
		my $factor = $pow2[20 + $exponent];
		print "$orig_bits $float  \t$exponent\t" . ($sign > 0 ? "+" : "-") 
				. "\t" . sprintf("%8x", $mantissa) 
				. "\t" . $mantissa / ($factor != 0 ? $factor : 1)
				. "\t" . sprintf("%08x", $float)
				. "\n";
	} # while DATA
	
__DATA__
{F 10000000,11110000,00000000,00000000} 7.5		
{F 11111100,00000000,00000000,00000000} .5		
{F 11110100,11001100,11001100,11001101} .1		
{F 11110100,10011000,11001100,11001101} .05		
{F 11110000,10001100,11010111,10100000}	.01		
{F 11101100,10100011,11010111,10100000} .005	
{F 11101000,10000011,10010000,11011110} .001	
