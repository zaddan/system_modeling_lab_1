#include "susan.sh"

import "c_uchar7220_queue";

behavior EdgeDrawThread_PartA(uchar image_buffer[7220], uchar mid[7220], in int thID)
{

    void main(void) {
    
        int   i;
        uchar *inp, *midp;
        int drawing_mode;
        
        drawing_mode = DRAWING_MODE;
        if (drawing_mode==0)
        {
            /* mark 3x3 white block around each edge point */
            midp=mid + IMAGE_SIZE/PROCESSORS *thID;
            for (i=X_SIZE*Y_SIZE/PROCESSORS*thID; i<X_SIZE*Y_SIZE/PROCESSORS*(thID+1) + (thID+1==PROCESSORS && X_SIZE*Y_SIZE%PROCESSORS!=0 ?X_SIZE*Y_SIZE%PROCESSORS : 0); i++)
            {
                if (*midp<8) 
                {
                    inp = image_buffer + (midp - mid) - X_SIZE - 1;
                    *inp++=255; *inp++=255; *inp=255; inp+=X_SIZE-2;
                    *inp++=255; *inp++;     *inp=255; inp+=X_SIZE-2;
                    *inp++=255; *inp++=255; *inp=255;
                }
                midp++;
            }
        }
     }   
   
};    


behavior EdgeDrawThread_PartB(uchar image_buffer[7220], uchar mid[7220], in int thID)
{

    void main(void) {
    
        int   i;
        uchar  *midp;
        int drawing_mode;
        
        drawing_mode = DRAWING_MODE;
     
        /* now mark 1 black pixel at each edge point */
        midp=mid+ IMAGE_SIZE/PROCESSORS *thID;
        //for (i=0; i<X_SIZE*Y_SIZE; i++)
        for (i=X_SIZE*Y_SIZE/PROCESSORS*thID; i<X_SIZE*Y_SIZE/PROCESSORS*(thID+1) + (thID+1==PROCESSORS && X_SIZE*Y_SIZE%PROCESSORS!=0 ?X_SIZE*Y_SIZE%PROCESSORS : 0); i++)
        {
            if (*midp<8) 
                *(image_buffer+ (midp - mid)) = 0;
            midp++;
        }
    }
    
};    

behavior EdgeDraw_ReadInput(i_uchar7220_receiver in_image, i_uchar7220_receiver in_mid, uchar image_buffer[IMAGE_SIZE], uchar mid[IMAGE_SIZE])
{
    void main(void) {
        in_image.receive(&image_buffer);
        in_mid.receive(&mid);
    }      
};

behavior EdgeDraw_WriteOutput(uchar image_buffer[IMAGE_SIZE],  i_uchar7220_sender out_image)
{
    void main(void) {
        out_image.send(image_buffer);
    }
};

behavior EdgeDraw_PartA(uchar image_buffer[7220], uchar mid[7220])
{

    EdgeDrawThread_PartA edge_draw_a_thread_0(image_buffer, mid, 0);
    EdgeDrawThread_PartA edge_draw_a_thread_1(image_buffer, mid, 1);
    
    void main(void) {
      par {
            edge_draw_a_thread_0;
            edge_draw_a_thread_1;
        }    
    }     
};

behavior EdgeDraw_PartB(uchar image_buffer[7220], uchar mid[7220])
{

    EdgeDrawThread_PartB edge_draw_b_thread_0(image_buffer, mid, 0);
    EdgeDrawThread_PartB edge_draw_b_thread_1(image_buffer, mid, 1);
    
    void main(void) {
      par {
            edge_draw_b_thread_0;
            edge_draw_b_thread_1;
        }    
    }     
};


behavior EdgeDraw(i_uchar7220_receiver in_image, i_uchar7220_receiver in_mid,  i_uchar7220_sender out_image)
{

    
    uchar image_buffer[IMAGE_SIZE];
    uchar mid[IMAGE_SIZE];         
    
    EdgeDraw_ReadInput edge_draw_read_input(in_image, in_mid, image_buffer, mid);
    EdgeDraw_WriteOutput edge_draw_write_output(image_buffer, out_image);
    EdgeDraw_PartA edge_draw_a(image_buffer, mid);
    EdgeDraw_PartB edge_draw_b(image_buffer, mid);

    
    void main(void) {
    
        fsm{
            edge_draw_read_input: goto edge_draw_a;
            edge_draw_a: goto edge_draw_b;
            edge_draw_b: goto edge_draw_write_output;
            edge_draw_write_output: {}
        }
    }     
    
};    

behavior Draw(i_uchar7220_receiver in_image, i_uchar7220_receiver in_mid,  i_uchar7220_sender out_image)
{

    EdgeDraw edge_draw(in_image, in_mid,  out_image);
    
    void main(void) {
        fsm {
            edge_draw: {goto edge_draw;}
        }
    }
    
};


