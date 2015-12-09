#ifndef	__DATA_RECORD_HEADER__
#define	__DATA_RECORD_HEADER__

struct DataRecord {
	int id;
	char name[24];
	char phone[10];
	short nextByName;
};

typedef struct DataRecord DataRecord;

#endif
