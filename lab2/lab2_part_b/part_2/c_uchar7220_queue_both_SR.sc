#include "c_typed_queue_both_SR.sh"	/* make the template available */
//#include "c_typed_queue_R.sh"	/* make the template available */
//#include "c_typed_queue_S.sh"	/* make the template available */

#include <i_typed_sender.sh>
#include <i_typed_receiver.sh>
#include <i_typed_tranceiver.sh>

typedef unsigned char  uchar7220[7220];
DEFINE_I_TYPED_TRANCEIVER(uchar7220, uchar7220)
DEFINE_I_TYPED_SENDER(uchar7220, uchar7220)
DEFINE_I_TYPED_RECEIVER(uchar7220, uchar7220)
DEFINE_C_TYPED_QUEUE(uchar7220, uchar7220)

