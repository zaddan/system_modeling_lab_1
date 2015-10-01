import "c_double_handshake";
import "c_queue";
import "stimulus";
import "monitor";
import "design";
#include "constant.hh"

behavior Main(void)
{
  const unsigned long  trigger_size = 1;
  const unsigned long  img_size = IMAGE_SIZE;
  const unsigned long  img_size4 = 4*IMAGE_SIZE;
  c_double_handshake Trigger;
  
  c_queue imageBuffer(img_size);
  c_double_handshake stimulusStart;
  c_queue finalOutput(img_size);
  
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
