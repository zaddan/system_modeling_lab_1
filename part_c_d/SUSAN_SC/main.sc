
import "c_double_handshake";
//import "c_queue";
import "c_image_queue";
import "susan_thin";
import "stimulus";
import "edge_draw";
import "monitor";
import "detect_edges";
import "design";
#include "constant.hh"

behavior Main(void)
{
  const unsigned long  trigger_size = 1;
  const unsigned long  img_size = IMAGE_SIZE;
  const unsigned long  img_size4 = 4*IMAGE_SIZE;
  c_double_handshake Trigger;
  
//  c_queue imageBuffer(img_size4);
//  c_queue finalOutput(img_size);
 
  c_image_queue imageBuffer(img_size4); 
  c_image_queue finalOutput(img_size4);
  c_double_handshake stimulusStart;
 
  stimulus myStimulus(imageBuffer, stimulusStart);
  design myDesign(stimulusStart, imageBuffer, finalOutput) ;
  monitor myMonitor(finalOutput);  
  
  int main(void) {
      printf("start getting the image\n");
      par {
          myStimulus;
          myDesign; 
          myMonitor;
      }
      return(0);
  }
};
