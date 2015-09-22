#include "config.h"
//need this one
behavior edge_draw(i_receiver inPort_in, i_sender outPort):
{

    int x_size = X_SIZE_CONST;
    int y_size = Y_SIZE_CONST;
    void main(void) { 
    uchar in [IMSGE_SIZE_CONST];
    uchar mid [IMSGE_SIZE_CONST];
    inPort.receive(in, IMAGE_SIZE_CONST);
    inPort.receive(mid, IMAGE_SIZE_CONST);
    
    int   i;
    uchar *inp, *midp;

  if (drawing_mode==0)
  {
    /* mark 3x3 white block around each edge point */
    midp=mid;
    for (i=0; i<x_size*y_size; i++)
    {
      if (*midp<8) 
      {
        inp = in + (midp - mid) - x_size - 1;
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
      *(in + (midp - mid)) = 0;
    midp++;
  }

  outPort.send(in, IMAGE_SIZE_CONST);

}
};
