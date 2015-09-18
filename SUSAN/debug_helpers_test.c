#include <stdlib.h>                                                                                
#include <stdio.h>                                                                                 
#include <string.h>                                                                                
#include <math.h>                                                                                  
#include <sys/file.h>    /* may want to remove this line */                                        
#include <malloc.h>      /* may want to remove this line */                                        
#include "config.h"
#include "debug_helpers.h"

int main(){
  const char *outputFileName = "myOutput.txt";
  const char mode = "r";
  char *word = "testing";
  print_output(outputFileName, mode, word);
}
