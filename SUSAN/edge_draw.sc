#include "constant.hh"
import "c_double_handshake";
import "c_queue";

//need this one

behavior edge_draw(i_receiver inPort, i_receiver image, i_sender outPort )
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
    inPort.receive(mid, IMAGE_SIZE);
    image.receive(imageData, IMAGE_SIZE);
    

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
  
  outPort.send(imageData, IMAGE_SIZE);

}
};
