#include "constant.hh"
import "c_double_handshake";
import "c_queue";
#include <sys/file.h>    /* may want to remove this line */
#include <stdio.h>    /* may want to remove this line */
behavior monitor(i_receiver inPort){
    void main(void){ 
    char imageIn[IMAGE_SIZE];
    char  filename[FILE_NAME_SIZE] = "output_edge.pgm";
    FILE  *fd;
    int x_size =  X_SIZE_CONST;
    int y_size =  Y_SIZE_CONST;
    inPort.receive(imageIn, IMAGE_SIZE);


#ifdef FOPENB
    if ((fd=fopen(filename,"wb")) == NULL) 
#else
        if ((fd=fopen(filename,"w")) == NULL) 
#endif
       {}     //   exit_error("Can't output image%s.\n",filename);

    fprintf(fd,"P5\n");
    fprintf(fd,"%d %d\n",x_size,y_size);
    fprintf(fd,"255\n");

    if (fwrite(imageIn,x_size*y_size,1,fd) != 1)
       ; 
       // exit_error("Can't write image %s.\n",filename);

    fclose(fd);
}
};
