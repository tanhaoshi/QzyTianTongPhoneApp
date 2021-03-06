// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneSimCard.proto

#ifndef PROTOBUF_TtPhoneSimCard_2eproto__INCLUDED
#define PROTOBUF_TtPhoneSimCard_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3001000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3001000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)

namespace phonedata {

// Internal implementation detail -- do not call these.
void protobuf_AddDesc_TtPhoneSimCard_2eproto();
void protobuf_InitDefaults_TtPhoneSimCard_2eproto();
void protobuf_AssignDesc_TtPhoneSimCard_2eproto();
void protobuf_ShutdownFile_TtPhoneSimCard_2eproto();

class TtPhoneSimCard;

// ===================================================================

class TtPhoneSimCard : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.TtPhoneSimCard) */ {
 public:
  TtPhoneSimCard();
  virtual ~TtPhoneSimCard();

  TtPhoneSimCard(const TtPhoneSimCard& from);

  inline TtPhoneSimCard& operator=(const TtPhoneSimCard& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const TtPhoneSimCard& default_instance();

  static const TtPhoneSimCard* internal_default_instance();

  void Swap(TtPhoneSimCard* other);

  // implements Message ----------------------------------------------

  inline TtPhoneSimCard* New() const { return New(NULL); }

  TtPhoneSimCard* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const TtPhoneSimCard& from);
  void MergeFrom(const TtPhoneSimCard& from);
  void Clear();
  bool IsInitialized() const;

  size_t ByteSizeLong() const;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input);
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* output) const;
  ::google::protobuf::uint8* SerializeWithCachedSizesToArray(::google::protobuf::uint8* output) const {
    return InternalSerializeWithCachedSizesToArray(false, output);
  }
  int GetCachedSize() const { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const;
  void InternalSwap(TtPhoneSimCard* other);
  void UnsafeMergeFrom(const TtPhoneSimCard& from);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return _internal_metadata_.arena();
  }
  inline void* MaybeArenaPtr() const {
    return _internal_metadata_.raw_arena_ptr();
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // optional bool isSimCard = 1;
  void clear_issimcard();
  static const int kIsSimCardFieldNumber = 1;
  bool issimcard() const;
  void set_issimcard(bool value);

  // @@protoc_insertion_point(class_scope:phonedata.TtPhoneSimCard)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  bool issimcard_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtPhoneSimCard_2eproto_impl();
  friend void  protobuf_AddDesc_TtPhoneSimCard_2eproto_impl();
  friend void protobuf_AssignDesc_TtPhoneSimCard_2eproto();
  friend void protobuf_ShutdownFile_TtPhoneSimCard_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<TtPhoneSimCard> TtPhoneSimCard_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// TtPhoneSimCard

// optional bool isSimCard = 1;
inline void TtPhoneSimCard::clear_issimcard() {
  issimcard_ = false;
}
inline bool TtPhoneSimCard::issimcard() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneSimCard.isSimCard)
  return issimcard_;
}
inline void TtPhoneSimCard::set_issimcard(bool value) {
  
  issimcard_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneSimCard.isSimCard)
}

inline const TtPhoneSimCard* TtPhoneSimCard::internal_default_instance() {
  return &TtPhoneSimCard_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_TtPhoneSimCard_2eproto__INCLUDED
