#include "constant.hh"
#include <stdio.h>
import "wait_behav";
import "c_double_handshake";
import "c_queue";
import "c_image_queue";

//need this one

behavior edge_draw(i_receiver inPort, i_receiver image, i_splitImage_sender outPort)
{
    int x_size = X_SIZE_CONST;
    int y_size = Y_SIZE_CONST;
    void main(void) { 
        int i;
        int drawing_mode = 0; 
        unsigned char imageData[splitSize];
        unsigned char mid[splitSize];
        unsigned char *inp;
        unsigned char *midp;
        inPort.receive(mid, splitSize);
        image.receive(imageData, splitSize);


        if (drawing_mode==0)
        {
            /* mark 3x3 white block around each edge point */
            midp=mid;
            for (i=0; i< (IMAGE_SIZE)/2; i++)
            {
                if (*midp<8) 
                {
                    inp = imageData + (midp - mid) - x_size - 1;
                    *inp++=255; *inp++=255; *inp=255; inp+=x_size-2;
                    *inp++=255; *inp++;     *inp=255; inp+=x_size-2;
                    *inp++=255; *inp++=255; *inp=255;
                }
                midp++;
            }
        }

        /* now mark 1 black pixel at each edge point */
        midp=mid;
        for (i=0; i< (IMAGE_SIZE/2); i++)
        {
            if (*midp<8) 
                *(imageData + (midp - mid)) = 0;
            midp++;
        }
        outPort.send(imageData);
    
    }
};


behavior edge_draw_fsm(i_receiver susanThinOutput1, i_receiver susanThinOutput2, i_receiver half_imageReadOut2_1, i_receiver half_imageReadOut2_2, i_splitImage_sender imageSusanOut1, i_splitImage_sender imageSusanOut2)
{
   edge_draw myEdgeDraw1(susanThinOutput1, half_imageReadOut2_1,imageSusanOut1);
   edge_draw myEdgeDraw2(susanThinOutput2, half_imageReadOut2_2,imageSusanOut2);
   void main(void) {
        fsm {                            
                myEdgeDraw1:{ 
                    goto myEdgeDraw2;
                            }
                myEdgeDraw2:{ 
                    goto myEdgeDraw1;
                            }

            }
    }
};
