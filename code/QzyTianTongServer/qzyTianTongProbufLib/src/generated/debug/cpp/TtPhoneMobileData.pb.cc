// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneMobileData.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "TtPhoneMobileData.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/port.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)

namespace phonedata {

namespace {

const ::google::protobuf::Descriptor* TtPhoneMobileData_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  TtPhoneMobileData_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_TtPhoneMobileData_2eproto() GOOGLE_ATTRIBUTE_COLD;
void protobuf_AssignDesc_TtPhoneMobileData_2eproto() {
  protobuf_AddDesc_TtPhoneMobileData_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "TtPhoneMobileData.proto");
  GOOGLE_CHECK(file != NULL);
  TtPhoneMobileData_descriptor_ = file->message_type(0);
  static const int TtPhoneMobileData_offsets_[2] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtPhoneMobileData, isenabledata_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtPhoneMobileData, responsestatus_),
  };
  TtPhoneMobileData_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      TtPhoneMobileData_descriptor_,
      TtPhoneMobileData::internal_default_instance(),
      TtPhoneMobileData_offsets_,
      -1,
      -1,
      -1,
      sizeof(TtPhoneMobileData),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TtPhoneMobileData, _internal_metadata_));
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_TtPhoneMobileData_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) GOOGLE_ATTRIBUTE_COLD;
void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      TtPhoneMobileData_descriptor_, TtPhoneMobileData::internal_default_instance());
}

}  // namespace

void protobuf_ShutdownFile_TtPhoneMobileData_2eproto() {
  TtPhoneMobileData_default_instance_.Shutdown();
  delete TtPhoneMobileData_reflection_;
}

void protobuf_InitDefaults_TtPhoneMobileData_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  TtPhoneMobileData_default_instance_.DefaultConstruct();
  TtPhoneMobileData_default_instance_.get_mutable()->InitAsDefaultInstance();
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_InitDefaults_TtPhoneMobileData_2eproto_once_);
void protobuf_InitDefaults_TtPhoneMobileData_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_InitDefaults_TtPhoneMobileData_2eproto_once_,
                 &protobuf_InitDefaults_TtPhoneMobileData_2eproto_impl);
}
void protobuf_AddDesc_TtPhoneMobileData_2eproto_impl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  protobuf_InitDefaults_TtPhoneMobileData_2eproto();
  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\027TtPhoneMobileData.proto\022\tphonedata\"A\n\021"
    "TtPhoneMobileData\022\024\n\014isEnableData\030\001 \001(\010\022"
    "\026\n\016responseStatus\030\002 \001(\010B*\n\017com.qzy.tt.da"
    "taB\027TtPhoneMobileDataProtosb\006proto3", 155);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "TtPhoneMobileData.proto", &protobuf_RegisterTypes);
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_TtPhoneMobileData_2eproto);
}

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AddDesc_TtPhoneMobileData_2eproto_once_);
void protobuf_AddDesc_TtPhoneMobileData_2eproto() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AddDesc_TtPhoneMobileData_2eproto_once_,
                 &protobuf_AddDesc_TtPhoneMobileData_2eproto_impl);
}
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_TtPhoneMobileData_2eproto {
  StaticDescriptorInitializer_TtPhoneMobileData_2eproto() {
    protobuf_AddDesc_TtPhoneMobileData_2eproto();
  }
} static_descriptor_initializer_TtPhoneMobileData_2eproto_;

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD GOOGLE_ATTRIBUTE_NORETURN;
static void MergeFromFail(int line) {
  ::google::protobuf::internal::MergeFromFail(__FILE__, line);
}

}  // namespace


// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int TtPhoneMobileData::kIsEnableDataFieldNumber;
const int TtPhoneMobileData::kResponseStatusFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

TtPhoneMobileData::TtPhoneMobileData()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  if (this != internal_default_instance()) protobuf_InitDefaults_TtPhoneMobileData_2eproto();
  SharedCtor();
  // @@protoc_insertion_point(constructor:phonedata.TtPhoneMobileData)
}

void TtPhoneMobileData::InitAsDefaultInstance() {
}

TtPhoneMobileData::TtPhoneMobileData(const TtPhoneMobileData& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  UnsafeMergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:phonedata.TtPhoneMobileData)
}

void TtPhoneMobileData::SharedCtor() {
  ::memset(&isenabledata_, 0, reinterpret_cast<char*>(&responsestatus_) -
    reinterpret_cast<char*>(&isenabledata_) + sizeof(responsestatus_));
  _cached_size_ = 0;
}

TtPhoneMobileData::~TtPhoneMobileData() {
  // @@protoc_insertion_point(destructor:phonedata.TtPhoneMobileData)
  SharedDtor();
}

void TtPhoneMobileData::SharedDtor() {
}

void TtPhoneMobileData::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* TtPhoneMobileData::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return TtPhoneMobileData_descriptor_;
}

const TtPhoneMobileData& TtPhoneMobileData::default_instance() {
  protobuf_InitDefaults_TtPhoneMobileData_2eproto();
  return *internal_default_instance();
}

::google::protobuf::internal::ExplicitlyConstructed<TtPhoneMobileData> TtPhoneMobileData_default_instance_;

TtPhoneMobileData* TtPhoneMobileData::New(::google::protobuf::Arena* arena) const {
  TtPhoneMobileData* n = new TtPhoneMobileData;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void TtPhoneMobileData::Clear() {
// @@protoc_insertion_point(message_clear_start:phonedata.TtPhoneMobileData)
#if defined(__clang__)
#define ZR_HELPER_(f) \
  _Pragma("clang diagnostic push") \
  _Pragma("clang diagnostic ignored \"-Winvalid-offsetof\"") \
  __builtin_offsetof(TtPhoneMobileData, f) \
  _Pragma("clang diagnostic pop")
#else
#define ZR_HELPER_(f) reinterpret_cast<char*>(\
  &reinterpret_cast<TtPhoneMobileData*>(16)->f)
#endif

#define ZR_(first, last) do {\
  ::memset(&(first), 0,\
           ZR_HELPER_(last) - ZR_HELPER_(first) + sizeof(last));\
} while (0)

  ZR_(isenabledata_, responsestatus_);

#undef ZR_HELPER_
#undef ZR_

}

bool TtPhoneMobileData::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:phonedata.TtPhoneMobileData)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // optional bool isEnableData = 1;
      case 1: {
        if (tag == 8) {

          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &isenabledata_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(16)) goto parse_responseStatus;
        break;
      }

      // optional bool responseStatus = 2;
      case 2: {
        if (tag == 16) {
         parse_responseStatus:

          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   bool, ::google::protobuf::internal::WireFormatLite::TYPE_BOOL>(
                 input, &responsestatus_)));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectAtEnd()) goto success;
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0 ||
            ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormatLite::SkipField(input, tag));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:phonedata.TtPhoneMobileData)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:phonedata.TtPhoneMobileData)
  return false;
#undef DO_
}

void TtPhoneMobileData::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:phonedata.TtPhoneMobileData)
  // optional bool isEnableData = 1;
  if (this->isenabledata() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(1, this->isenabledata(), output);
  }

  // optional bool responseStatus = 2;
  if (this->responsestatus() != 0) {
    ::google::protobuf::internal::WireFormatLite::WriteBool(2, this->responsestatus(), output);
  }

  // @@protoc_insertion_point(serialize_end:phonedata.TtPhoneMobileData)
}

::google::protobuf::uint8* TtPhoneMobileData::InternalSerializeWithCachedSizesToArray(
    bool deterministic, ::google::protobuf::uint8* target) const {
  (void)deterministic; // Unused
  // @@protoc_insertion_point(serialize_to_array_start:phonedata.TtPhoneMobileData)
  // optional bool isEnableData = 1;
  if (this->isenabledata() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(1, this->isenabledata(), target);
  }

  // optional bool responseStatus = 2;
  if (this->responsestatus() != 0) {
    target = ::google::protobuf::internal::WireFormatLite::WriteBoolToArray(2, this->responsestatus(), target);
  }

  // @@protoc_insertion_point(serialize_to_array_end:phonedata.TtPhoneMobileData)
  return target;
}

size_t TtPhoneMobileData::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:phonedata.TtPhoneMobileData)
  size_t total_size = 0;

  // optional bool isEnableData = 1;
  if (this->isenabledata() != 0) {
    total_size += 1 + 1;
  }

  // optional bool responseStatus = 2;
  if (this->responsestatus() != 0) {
    total_size += 1 + 1;
  }

  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void TtPhoneMobileData::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:phonedata.TtPhoneMobileData)
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  const TtPhoneMobileData* source =
      ::google::protobuf::internal::DynamicCastToGenerated<const TtPhoneMobileData>(
          &from);
  if (source == NULL) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:phonedata.TtPhoneMobileData)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:phonedata.TtPhoneMobileData)
    UnsafeMergeFrom(*source);
  }
}

void TtPhoneMobileData::MergeFrom(const TtPhoneMobileData& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:phonedata.TtPhoneMobileData)
  if (GOOGLE_PREDICT_TRUE(&from != this)) {
    UnsafeMergeFrom(from);
  } else {
    MergeFromFail(__LINE__);
  }
}

void TtPhoneMobileData::UnsafeMergeFrom(const TtPhoneMobileData& from) {
  GOOGLE_DCHECK(&from != this);
  if (from.isenabledata() != 0) {
    set_isenabledata(from.isenabledata());
  }
  if (from.responsestatus() != 0) {
    set_responsestatus(from.responsestatus());
  }
}

void TtPhoneMobileData::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:phonedata.TtPhoneMobileData)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void TtPhoneMobileData::CopyFrom(const TtPhoneMobileData& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:phonedata.TtPhoneMobileData)
  if (&from == this) return;
  Clear();
  UnsafeMergeFrom(from);
}

bool TtPhoneMobileData::IsInitialized() const {

  return true;
}

void TtPhoneMobileData::Swap(TtPhoneMobileData* other) {
  if (other == this) return;
  InternalSwap(other);
}
void TtPhoneMobileData::InternalSwap(TtPhoneMobileData* other) {
  std::swap(isenabledata_, other->isenabledata_);
  std::swap(responsestatus_, other->responsestatus_);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata TtPhoneMobileData::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = TtPhoneMobileData_descriptor_;
  metadata.reflection = TtPhoneMobileData_reflection_;
  return metadata;
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// TtPhoneMobileData

// optional bool isEnableData = 1;
void TtPhoneMobileData::clear_isenabledata() {
  isenabledata_ = false;
}
bool TtPhoneMobileData::isenabledata() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneMobileData.isEnableData)
  return isenabledata_;
}
void TtPhoneMobileData::set_isenabledata(bool value) {
  
  isenabledata_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneMobileData.isEnableData)
}

// optional bool responseStatus = 2;
void TtPhoneMobileData::clear_responsestatus() {
  responsestatus_ = false;
}
bool TtPhoneMobileData::responsestatus() const {
  // @@protoc_insertion_point(field_get:phonedata.TtPhoneMobileData.responseStatus)
  return responsestatus_;
}
void TtPhoneMobileData::set_responsestatus(bool value) {
  
  responsestatus_ = value;
  // @@protoc_insertion_point(field_set:phonedata.TtPhoneMobileData.responseStatus)
}

inline const TtPhoneMobileData* TtPhoneMobileData::internal_default_instance() {
  return &TtPhoneMobileData_default_instance_.get();
}
#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)