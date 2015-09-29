#include "constant.hh"
#include "string.h"
#include <sim.sh>
import "c_double_handshake";
import "c_queue";

#include <sys/file.h>    /* may want to remove this line */
#include <stdio.h>    /* may want to remove this line */

sim_time t;
sim_delta d;
sim_time_string buffer;

behavior monitor(i_receiver inPort){
    void main(void){ 
        int terminateCounter; 
        for(terminateCounter = TERMINATE_COUNTER_MAX; terminateCounter > 0; terminateCounter -= TERMINATE_CONTER_STEP) { 
            char imageIn[IMAGE_SIZE];
            
            char myArray[6] = {'a', 'b', 'c', 'd', 'e', 'f'};
            FILE  *fd;
            char  filename[FILE_NAME_SIZE] = "output_edge.pgm";
            int x_size =  X_SIZE_CONST;
            int y_size =  Y_SIZE_CONST;
            inPort.receive(imageIn, IMAGE_SIZE);
            filename[terminateCounter] =  myArray[terminateCounter];
	    
	    t = now(); d = delta();
	    printf("Time is now %s pico seconds.\n", time2str(buffer,t));
           // strcat(filename, myChar);
            printf("%s \n", filename);

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
    }
};
