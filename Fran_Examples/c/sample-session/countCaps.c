#include <stdio.h>

int
countCapitals(char *s)
{
	int count = 0;
	int i;

	for (i = 0; s[i] != '\0'; i++)
	{
		if (s[i] >= 'A' && s[i] <= 'Z')
			count++;
	}

	return count;
}

int main()
{
	char *shortString = "This is Short";
	char *longString = "This string is very LOOOOONG";

	printf("Number of capitals in '%s' is %d\n",
			shortString, countCapitals(shortString));

	printf("Number of capitals in '%s' is %d\n",
			longString, countCapitals(longString));

	return (0);
}
