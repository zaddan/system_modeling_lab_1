#include "constant.h"
#include <sys/file.h>    /* may want to remove this line */
#include <stdio.h>    /* may want to remove this line */
import "c_double_handshake";
import "c_queue";
#include "get_int.h"
behavior stimulus(i_sender outPort, i_sender startSignal) {
    void main(void) { 
//     unsigned char  in_global[IMAGE_SIZE]; 
     
    char           filename[FILE_NAME_SIZE] = "input_small.pgm";
    char header [100];
    FILE  *fd;
    int  tmp;
    int dummy;
    int start; 
    //unsigned char in = in_global;
    unsigned char imageArray[IMAGE_SIZE];

//#ifdef FOPENB
//    if ((fd=fopen(filename,"rb")) == NULL)
//#else
//        if ((fd=fopen(filename,"r")) == NULL)
//#endif
//            exit_error("Can't input image %s.\n",filename);
//
//    /* {{{ read header */

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
    if (fread(imageArray,1,X_SIZE_CONST* Y_SIZE_CONST,fd) == 0) ;

     
     //   exit_error("Image %s is wrong size.\n",filename);

    fclose(fd);
    start = 1;
    outPort.send(imageArray, IMAGE_SIZE);
    outPort.send(imageArray, IMAGE_SIZE);
    startSignal.send((void *)&start, 1);
}
};
