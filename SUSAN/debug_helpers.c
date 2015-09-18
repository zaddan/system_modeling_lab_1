#include <stdlib.h>                                                                                
#include <stdio.h>                                                                                 
#include <string.h>                                                                                
#include <math.h>                                                                                  
#include <sys/file.h>    /* may want to remove this line */                                        
#include <malloc.h>      /* may want to remove this line */                                        
#include "config.h"
#include "debug_helpers.h"

int print_output(const char outputFileName[50], const char mode, char *word){
    FILE *ifp, *ofp;
    char modeConst[1]; 
    memset(modeConst, '\0', 1); 
    char wordConst[100]; 
    memset(wordConst, '\0', sizeof(wordConst)); 
    
    strcpy(modeConst, mode); 
    strcpy(wordConst, word); 
    printf("%s\n", wordConst);
    printf("%s\n", modeConst);
    printf("%s\n", outputFileName);

    return 0;    
//    enum STATUS  {SUCCESS, FAILURE }    
//    ifp = fopen(outputFilename, mode);
//
//    if (ofp == NULL) {
//        fprintf(stderr, "Can't open output file %s!\n",
//                outputFilename);
//        return FAILURE;
//    } 
//    
//    fprintf(ofp, "Integer: %s", word );
//    return SUCCESS;
}




