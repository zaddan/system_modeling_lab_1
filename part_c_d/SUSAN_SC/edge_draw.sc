#include "constant.hh"
import "c_double_handshake";
//import "c_queue";
import "c_image_queue";


//need this one

behavior edge_draw(i_image_receiver inPort, i_image_receiver image, i_image_sender outPort )
{
    int x_size = X_SIZE_CONST;
    int y_size = Y_SIZE_CONST;
    
    void main(void) { 
    int i;
    int drawing_mode = 0; 
    unsigned char imageData[IMAGE_SIZE];
    unsigned char mid[IMAGE_SIZE];
    unsigned char *inp;
    unsigned char *midp;
    inPort.receive(&mid);
    image.receive(&imageData);
    

  if (drawing_mode==0)
  {
    /* mark 3x3 white block around each edge point */
    midp=mid;
    for (i=0; i<x_size*y_size; i++)
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
  for (i=0; i<x_size*y_size; i++)
  {
    if (*midp<8) 
      *(imageData + (midp - mid)) = 0;
    midp++;
  }
  
  outPort.send(imageData);

}
};
