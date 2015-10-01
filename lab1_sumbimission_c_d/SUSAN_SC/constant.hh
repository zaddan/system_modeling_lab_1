#define BP_CONST 516 //migh have to modify later
#define X_SIZE_CONST 76
#define Y_SIZE_CONST 95
#define IMAGE_SIZE X_SIZE_CONST*Y_SIZE_CONST
#define FILE_NAME_SIZE 100 
#define MAX_NO_EDGES 2650
#define TERMINATE_COUNTER_MAX 100
#define TERMINATE_CONTER_STEP 1
#define offSet  133
#define splitSize IMAGE_SIZE/2 + offSet

typedef unsigned char img[IMAGE_SIZE];
typedef unsigned char splitImg[splitSize];
typedef unsigned char bpArray[BP_CONST];
typedef unsigned char bpArray[BP_CONST];
typedef int intR[IMAGE_SIZE];
