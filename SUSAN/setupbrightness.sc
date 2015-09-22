#include "config.h"
#include <stdio.h>
#include <stdlib.h>
##include "sim.sh"
#include <string.h>
import "c_queue"; 
import "c_double_handshake";

#define BP_SIZE 516
//need this one
//behavior setup_brightness_lut(bp,thresh,form)
//  uchar **bp;
//  int   thresh, form;
behavior setup_brightness_lut( i_sender out_port)  //out_port -> send bp, in_port -> receive thresh, form
{
	void main(main)
	{	
			int k, thresh,form;
			float temp;
			thresh = 20;
            form = 6; //TODO convert into constants
            //in_port.receive(&thresh,1);
			//in_port.receive(&form,1);
			//(unsigned char *)malloc(516);
			uchar bpArray[BP_SIZE];
			uchar *bp = bpArray;  //non constant pointer to start of array
			uchar *bp2 = bpArray;
			//*bp=*bp+258;
			bp=bp+258; // move pointer	
			for(k=-256;k<257;k++)
			{
					temp=((float)k)/((float)thresh);
					temp=temp*temp;
					if (form==6)
							temp=temp*temp*temp;
					temp=100.0*exp(-temp);
					//*(*bp+k)= (uchar)temp;
					*(bp+k)= (uchar)temp;

			}
			out_port.send(bp2,BP_SIZE);
	}
};




