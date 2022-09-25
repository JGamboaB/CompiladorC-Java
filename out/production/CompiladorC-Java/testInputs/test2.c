#include <stdlib.h>
#include <stdio.h>
double* balance(int * pArray[], int pLength)
{
	ñ±
    int total = 0;
	int i = pLength;
	double* result = malloc(i*sizeof(double));
	double* index = result;
	for(; i>=0; i--)
	{
		total += pArray[i];
		printf("i es: %d\n",i);
		printf("Total es: %d\n",total);
	}
	for(i=0; i<pLength; i++, index+1) 
	{
		*index = pArray[i] / total;
	}
	return result;
}
int main(int argc, char const *argv[])
{
    int arreglo[] = {2, 3, 9, 8};
	int length = (sizeof(arreglo)/sizeof(arreglo[5]));
    printf("Resultado es: %d\n",balance(&arreglo,length));
    return 0;
}