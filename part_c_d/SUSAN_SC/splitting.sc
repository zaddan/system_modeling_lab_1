#include "constant.hh"
#include <stdio.h>
import "c_queue";
behavior splitting(i_receiver inputFull, i_sender output_1, i_sender output_2)
{

	void main(void)
	{
	int i, j;
	//const unsigned long half_img_size = IMAGE_SIZE/2;
	unsigned char input[IMAGE_SIZE]; //input can be image(r) or mid.
	unsigned char output1[splitSize]; //1st half
	unsigned char output2[splitSize]; //2nd half

/* SPLITTLING IMAGE OPERATION */
  	inputFull.receive(input, IMAGE_SIZE);


	 for (i = 0 ; i < splitSize; i ++)
	 {
		output1[i] = input[i];
		output2[i] = input[IMAGE_SIZE/2 - offSet + i];
	
 	 }
/*  SPLITTING COMPLETE */
 
    output_1.send(output1, IMAGE_SIZE/2 + offSet);
	output_2.send(output2, splitSize);
   }

};



behavior  splitting_fsm(i_receiver imageReadOut2, i_sender half_imageReadOut2_1, i_sender half_imageReadOut2_2, i_receiver susanThinOutput, i_sender susanThinOutput_1, i_sender susanThinOutput_2) 
{

    splitting split_image(imageReadOut2, half_imageReadOut2_1, half_imageReadOut2_2); //split imageReadOut2
    splitting split_mid(susanThinOutput, susanThinOutput_1, susanThinOutput_2); // split susanThinOutput
    void main(void) {
        fsm {   
split_image: { 
                 goto split_mid;
             }
split_mid: {
               goto split_image;
            }
            }
    }
};



