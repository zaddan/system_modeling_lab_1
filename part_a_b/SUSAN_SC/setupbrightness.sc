#include <stdio.h>
#include <stdlib.h>
#include "sim.sh"
#include <string.h>
#include <math.h>
import "c_queue"; 
import "c_double_handshake";
#define BP_SIZE 516
behavior setup_brightness_lut(i_sender out_port)
{
	void main(void)
	{	
		 int oy;	
            int k, thresh,form;
			float temp;
			unsigned char bpArray[BP_SIZE];
            
            unsigned char *bp;  //non constant pointer to start of array
			unsigned char *bp2; 
            bp = bpArray; 
            bp2 = bpArray; 
            thresh = 20;
            form = 6; //TODO convert into constants
            //in_port.receive(&thresh,1);
			//in_port.receive(&form,1);
			//(unsigned char *)malloc(516);
			
			//*bp=*bp+258;
			bp=bp+258; // move pointer	
			for(k=-256;k<257;k++)
			{
					temp=((float)k)/((float)thresh);
                    temp=temp*temp;
                    //printf(":%f\n", temp); 
                    if (form==6)
							temp=temp*temp*temp;
					temp=100.0*exp(-temp);
					//*(*bp+k)= (unsigned char)temp;
                    //printf(":%f\n", temp); 
                    *(bp+k)= (unsigned char)temp;
                    //printf(":%c\n", (unsigned char)temp); 
			}
			
//            for (oy = 0; oy < 516; oy++){
//                printf("%d\n", bpArray[oy]);
//            }
            out_port.send(bpArray, BP_SIZE);
	
    }
};




