#include "constant.hh"
#include <sys/file.h>    /* may want to remove this line */
#include <stdio.h>    /* may want to remove this line */
import "c_double_handshake";
import "c_queue";
behavior stimulus(i_sender outPort1, i_sender startSignal, i_sender outPort2) {
    void main(void) { 
//     unsigned char  in_global[IMAGE_SIZE]; 
     
    char           filename[FILE_NAME_SIZE] = "input_small.pgm";
    char header [100];
    FILE  *fd;
    int  tmp;
    int start; 
    int c, i;
    char dummy[10000];
    int x = 0;
 
    //unsigned char in = in_global;
    unsigned char imageArray[IMAGE_SIZE];

#ifdef FOPENB
    if ((fd=fopen(filename,"rb")) == NULL)
#else
        if ((fd=fopen(filename,"r")) == NULL)
#endif
       {};//     exit_error("Can't input image %s.\n",filename);
//

    header[0]=fgetc(fd);
    header[1]=fgetc(fd);

//    if(!(header[0]=='P' && header[1]=='5'))
//        exit_error("Image %s does not have binary PGM header.\n",filename);

//    int x_size =  X_SIZE_CONST;
//    int y_size =  Y_SIZE_CONST;

//    dummy = getint(fd); //necessary cause it calls fgetc
//    dummy = getint(fd);
//    tmp = getint(fd);
//    
    
   
 /**right here*/
 
  c = getc(fd);
  x +=1; 
  while (1) /* find next integer */
  {
      x+=1; 
      if (c=='#') {   /* if we're at a comment, read to end of line */
      fgets(dummy,9000,fd);
       printf("righthere\n"); 
      }
    if (c==EOF);
    if (c>='0' && c<='9')
      break;   /* found what we were looking for */
    c = getc(fd);
  }

  /* we're at the start of a number, continue until we hit a non-number */
  i = 0;
  while (1) {
   x+=1; 
    i = (i*10) + (c - '0');
    c = getc(fd);
    if (c==EOF) ;
    if (c<'0' || c>'9') break;
  }c = getc(fd);
  x +=1; 
  while (1) /* find next integer */
  {
      x+=1; 
      if (c=='#') {   /* if we're at a comment, read to end of line */
      fgets(dummy,9000,fd);
       printf("righthere\n"); 
      }
    if (c==EOF);
    if (c>='0' && c<='9')
      break;   /* found what we were looking for */
    c = getc(fd);
  }

  /* we're at the start of a number, continue until we hit a non-number */
  i = 0;
  while (1) {
   x+=1; 
    i = (i*10) + (c - '0');
    c = getc(fd);
    if (c==EOF) ;
    if (c<'0' || c>'9') break;
  }
 

 /**till here*/







    if (fread(imageArray,1,X_SIZE_CONST* Y_SIZE_CONST,fd) == 0) ;

     
     //   exit_error("Image %s is wrong size.\n",filename);

    fclose(fd);
    start = 1;
    outPort1.send(imageArray, IMAGE_SIZE);
    outPort2.send(imageArray, IMAGE_SIZE);
    startSignal.send((void *)&start, 1);
}
};
