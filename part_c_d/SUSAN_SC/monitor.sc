#include "constant.hh"
import "c_double_handshake";
//import "c_queue";
import "c_image_queue";
#include <sys/file.h>    /* may want to remove this line */
#include <stdio.h>    /* may want to remove this line */
#include <sim.sh>


behavior monitor(i_image_receiver imgIn){
   
 void main(void){ 
    sim_time t;
    sim_delta d;
    sim_time_string buffer;
     
    unsigned char imageIn[IMAGE_SIZE];
    char  filename[FILE_NAME_SIZE] = "output_edge.pgm";
    FILE  *fd;
    int x_size =  X_SIZE_CONST;
    int y_size =  Y_SIZE_CONST;
    imgIn.receive(&imageIn);
    printf("Time is now %s pico seconds. \n", time2str(buffer, now()));

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
