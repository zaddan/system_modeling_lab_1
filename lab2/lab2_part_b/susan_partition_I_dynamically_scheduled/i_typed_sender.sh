// i_typed_sender.sh: template (macro) for a typed sender interface
//
// author: Rainer Doemer
//
// modifications: (most recent first)
//
// 04/25/03 RD	changed receiver interface to support array types
// 04/22/03 RD	added comment about non-support of array types
// 02/13/02 RD	applied naming convention, integrated with distribution
// 02/08/02 RD	converted into macro version
// 02/05/02 RD	initial version (based on queue.sc)
//
//
// template instantiation parameters (macro arguments):
//
// - type:     the SpecC type of the data to be transferred (i.e. bit[64]);
//             valid types are all basic and composite types including arrays
// - typename: identifier describing the data type (i.e. bit64)
//
// interface rules:
//
// - a connected thread acts as a sender
// - a call to send() sends out a packet of data to a connected channel
// - data packets must be of the specified type
// - calling send() may suspend the calling thread indefinitely


#ifndef I_TYPED_SENDER_SH
#define I_TYPED_SENDER_SH


#define DEFINE_I_TYPED_SENDER(typename, type)				\
									\
interface i_ ## typename ## _sender					\
{									\
    note _SCE_STANDARD_LIB = { "i_sender", #typename, #type };		\
									\
    void send(type d);							\
};


#endif /* I_TYPED_SENDER_SH */


// EOF i_typed_sender.sh
