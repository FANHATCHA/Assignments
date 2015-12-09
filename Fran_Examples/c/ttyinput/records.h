#ifndef	__DATA_RECORD_HEADER__
#define	__DATA_RECORD_HEADER__

#include <time.h>	/* for time_t */


#define		MAX_NAME_LEN		80
#define		MAX_EMAIL_LEN		48
#define		MAX_PHONE_LEN		10

struct PhonebookRecord {
	int id;
	char name[MAX_NAME_LEN];
	char email[MAX_EMAIL_LEN];
	char phone[MAX_PHONE_LEN];
	time_t birthday;
};

typedef struct PhonebookRecord PhonebookRecord;


/** function prototypes */
int inputRecordFromUser(PhonebookRecord *record);
int printRecord(FILE *ofp, PhonebookRecord *record);

int parseNameFromBuffer(PhonebookRecord *record, char *line, int size);
int parsePhonenumberFromBuffer(PhonebookRecord *record, char *line, int size);
int parseEmailFromBuffer(PhonebookRecord *record, char *line, int size);
int parseBirthdayFromBuffer(PhonebookRecord *record, char *line, int size);

#endif
