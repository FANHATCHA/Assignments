/*
 * ------------------------------------------------------------
 * Bitstring allocation and access tools
 * ------------------------------------------------------------
 * $Id: bitstring.h 417 2007-03-23 16:59:05Z andrew $
 */

#ifndef         __BITSTRING_HEADER__
#define         __BITSTRING_HEADER__

#include <stdlib.h>

/** the bits will be held in a string of octets (chars) */
typedef unsigned char *BITSTRING;

/**
 ** MACRO DEFINITIONS
 **/

#define	BITSTRING_LEN(nBits)	((nBits + 7) / 8)
#define	ALLOC_BITSTRING(nBits)	\
    		((unsigned char *) malloc(BITSTRING_LEN(nBits)))
#define	FREE_BITSTRING(s)		free(s)
#define	ZERO_BITSTRING(s,nBits)	memset(s, 0, BITSTRING_LEN(nBits))


#define GET_BIT(s,i)	((int) (((s[i/8]) >> (i % 8)) & 0x01))
#define CLR_BIT(s,i)	(s[i/8] = (s[i/8] & ~(0x01 << (i % 8)))
#define SET_BIT(s,i,v)	(s[i/8] = \
			((s[i/8] & ~(0x01 << (i % 8))) \
			 | (((v == 0) ? 0x00 : 0x01) << (i % 8))))

#endif  /* __BITSTRING_HEADER__ */
