/**
 * A structure for a phonebook entry
 */

#ifndef	__PHONEBOOK_HEADER__
#define	__PHONEBOOK_HEADER__

struct PhonebookEntry {
	char name[64];
	char phone[10];
	char email[24];
	int age;
};

typedef struct PhonebookEntry PhonebookEntry;

#endif
