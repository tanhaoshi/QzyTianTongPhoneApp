// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TtPhoneUpdateSendFile.proto

#ifndef PROTOBUF_TtPhoneUpdateSendFile_2eproto__INCLUDED
#define PROTOBUF_TtPhoneUpdateSendFile_2eproto__INCLUDED

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
void protobuf_AddDesc_TtPhoneUpdateSendFile_2eproto();
void protobuf_InitDefaults_TtPhoneUpdateSendFile_2eproto();
void protobuf_AssignDesc_TtPhoneUpdateSendFile_2eproto();
void protobuf_ShutdownFile_TtPhoneUpdateSendFile_2eproto();

class UpdateSendFile;
class UpdateSendFile_PFile;

// ===================================================================

class UpdateSendFile_PFile : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.UpdateSendFile.PFile) */ {
 public:
  UpdateSendFile_PFile();
  virtual ~UpdateSendFile_PFile();

  UpdateSendFile_PFile(const UpdateSendFile_PFile& from);

  inline UpdateSendFile_PFile& operator=(const UpdateSendFile_PFile& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const UpdateSendFile_PFile& default_instance();

  static const UpdateSendFile_PFile* internal_default_instance();

  void Swap(UpdateSendFile_PFile* other);

  // implements Message ----------------------------------------------

  inline UpdateSendFile_PFile* New() const { return New(NULL); }

  UpdateSendFile_PFile* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const UpdateSendFile_PFile& from);
  void MergeFrom(const UpdateSendFile_PFile& from);
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
  void InternalSwap(UpdateSendFile_PFile* other);
  void UnsafeMergeFrom(const UpdateSendFile_PFile& from);
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

  // optional string filename = 1;
  void clear_filename();
  static const int kFilenameFieldNumber = 1;
  const ::std::string& filename() const;
  void set_filename(const ::std::string& value);
  void set_filename(const char* value);
  void set_filename(const char* value, size_t size);
  ::std::string* mutable_filename();
  ::std::string* release_filename();
  void set_allocated_filename(::std::string* filename);

  // optional uint32 size = 2;
  void clear_size();
  static const int kSizeFieldNumber = 2;
  ::google::protobuf::uint32 size() const;
  void set_size(::google::protobuf::uint32 value);

  // optional bytes data = 3;
  void clear_data();
  static const int kDataFieldNumber = 3;
  const ::std::string& data() const;
  void set_data(const ::std::string& value);
  void set_data(const char* value);
  void set_data(const void* value, size_t size);
  ::std::string* mutable_data();
  ::std::string* release_data();
  void set_allocated_data(::std::string* data);

  // @@protoc_insertion_point(class_scope:phonedata.UpdateSendFile.PFile)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::ArenaStringPtr filename_;
  ::google::protobuf::internal::ArenaStringPtr data_;
  ::google::protobuf::uint32 size_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtPhoneUpdateSendFile_2eproto_impl();
  friend void  protobuf_AddDesc_TtPhoneUpdateSendFile_2eproto_impl();
  friend void protobuf_AssignDesc_TtPhoneUpdateSendFile_2eproto();
  friend void protobuf_ShutdownFile_TtPhoneUpdateSendFile_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<UpdateSendFile_PFile> UpdateSendFile_PFile_default_instance_;

// -------------------------------------------------------------------

class UpdateSendFile : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:phonedata.UpdateSendFile) */ {
 public:
  UpdateSendFile();
  virtual ~UpdateSendFile();

  UpdateSendFile(const UpdateSendFile& from);

  inline UpdateSendFile& operator=(const UpdateSendFile& from) {
    CopyFrom(from);
    return *this;
  }

  static const ::google::protobuf::Descriptor* descriptor();
  static const UpdateSendFile& default_instance();

  static const UpdateSendFile* internal_default_instance();

  void Swap(UpdateSendFile* other);

  // implements Message ----------------------------------------------

  inline UpdateSendFile* New() const { return New(NULL); }

  UpdateSendFile* New(::google::protobuf::Arena* arena) const;
  void CopyFrom(const ::google::protobuf::Message& from);
  void MergeFrom(const ::google::protobuf::Message& from);
  void CopyFrom(const UpdateSendFile& from);
  void MergeFrom(const UpdateSendFile& from);
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
  void InternalSwap(UpdateSendFile* other);
  void UnsafeMergeFrom(const UpdateSendFile& from);
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

  typedef UpdateSendFile_PFile PFile;

  // accessors -------------------------------------------------------

  // optional string ip = 1;
  void clear_ip();
  static const int kIpFieldNumber = 1;
  const ::std::string& ip() const;
  void set_ip(const ::std::string& value);
  void set_ip(const char* value);
  void set_ip(const char* value, size_t size);
  ::std::string* mutable_ip();
  ::std::string* release_ip();
  void set_allocated_ip(::std::string* ip);

  // optional .phonedata.UpdateSendFile.PFile fileData = 2;
  bool has_filedata() const;
  void clear_filedata();
  static const int kFileDataFieldNumber = 2;
  const ::phonedata::UpdateSendFile_PFile& filedata() const;
  ::phonedata::UpdateSendFile_PFile* mutable_filedata();
  ::phonedata::UpdateSendFile_PFile* release_filedata();
  void set_allocated_filedata(::phonedata::UpdateSendFile_PFile* filedata);

  // optional bool isSendFileFinish = 3;
  void clear_issendfilefinish();
  static const int kIsSendFileFinishFieldNumber = 3;
  bool issendfilefinish() const;
  void set_issendfilefinish(bool value);

  // @@protoc_insertion_point(class_scope:phonedata.UpdateSendFile)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::ArenaStringPtr ip_;
  ::phonedata::UpdateSendFile_PFile* filedata_;
  bool issendfilefinish_;
  mutable int _cached_size_;
  friend void  protobuf_InitDefaults_TtPhoneUpdateSendFile_2eproto_impl();
  friend void  protobuf_AddDesc_TtPhoneUpdateSendFile_2eproto_impl();
  friend void protobuf_AssignDesc_TtPhoneUpdateSendFile_2eproto();
  friend void protobuf_ShutdownFile_TtPhoneUpdateSendFile_2eproto();

  void InitAsDefaultInstance();
};
extern ::google::protobuf::internal::ExplicitlyConstructed<UpdateSendFile> UpdateSendFile_default_instance_;

// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
// UpdateSendFile_PFile

// optional string filename = 1;
inline void UpdateSendFile_PFile::clear_filename() {
  filename_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& UpdateSendFile_PFile::filename() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateSendFile.PFile.filename)
  return filename_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void UpdateSendFile_PFile::set_filename(const ::std::string& value) {
  
  filename_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.UpdateSendFile.PFile.filename)
}
inline void UpdateSendFile_PFile::set_filename(const char* value) {
  
  filename_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.UpdateSendFile.PFile.filename)
}
inline void UpdateSendFile_PFile::set_filename(const char* value, size_t size) {
  
  filename_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.UpdateSendFile.PFile.filename)
}
inline ::std::string* UpdateSendFile_PFile::mutable_filename() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.UpdateSendFile.PFile.filename)
  return filename_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* UpdateSendFile_PFile::release_filename() {
  // @@protoc_insertion_point(field_release:phonedata.UpdateSendFile.PFile.filename)
  
  return filename_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void UpdateSendFile_PFile::set_allocated_filename(::std::string* filename) {
  if (filename != NULL) {
    
  } else {
    
  }
  filename_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), filename);
  // @@protoc_insertion_point(field_set_allocated:phonedata.UpdateSendFile.PFile.filename)
}

// optional uint32 size = 2;
inline void UpdateSendFile_PFile::clear_size() {
  size_ = 0u;
}
inline ::google::protobuf::uint32 UpdateSendFile_PFile::size() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateSendFile.PFile.size)
  return size_;
}
inline void UpdateSendFile_PFile::set_size(::google::protobuf::uint32 value) {
  
  size_ = value;
  // @@protoc_insertion_point(field_set:phonedata.UpdateSendFile.PFile.size)
}

// optional bytes data = 3;
inline void UpdateSendFile_PFile::clear_data() {
  data_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& UpdateSendFile_PFile::data() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateSendFile.PFile.data)
  return data_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void UpdateSendFile_PFile::set_data(const ::std::string& value) {
  
  data_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.UpdateSendFile.PFile.data)
}
inline void UpdateSendFile_PFile::set_data(const char* value) {
  
  data_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.UpdateSendFile.PFile.data)
}
inline void UpdateSendFile_PFile::set_data(const void* value, size_t size) {
  
  data_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.UpdateSendFile.PFile.data)
}
inline ::std::string* UpdateSendFile_PFile::mutable_data() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.UpdateSendFile.PFile.data)
  return data_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* UpdateSendFile_PFile::release_data() {
  // @@protoc_insertion_point(field_release:phonedata.UpdateSendFile.PFile.data)
  
  return data_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void UpdateSendFile_PFile::set_allocated_data(::std::string* data) {
  if (data != NULL) {
    
  } else {
    
  }
  data_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), data);
  // @@protoc_insertion_point(field_set_allocated:phonedata.UpdateSendFile.PFile.data)
}

inline const UpdateSendFile_PFile* UpdateSendFile_PFile::internal_default_instance() {
  return &UpdateSendFile_PFile_default_instance_.get();
}
// -------------------------------------------------------------------

// UpdateSendFile

// optional string ip = 1;
inline void UpdateSendFile::clear_ip() {
  ip_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline const ::std::string& UpdateSendFile::ip() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateSendFile.ip)
  return ip_.GetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void UpdateSendFile::set_ip(const ::std::string& value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), value);
  // @@protoc_insertion_point(field_set:phonedata.UpdateSendFile.ip)
}
inline void UpdateSendFile::set_ip(const char* value) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ::std::string(value));
  // @@protoc_insertion_point(field_set_char:phonedata.UpdateSendFile.ip)
}
inline void UpdateSendFile::set_ip(const char* value, size_t size) {
  
  ip_.SetNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(),
      ::std::string(reinterpret_cast<const char*>(value), size));
  // @@protoc_insertion_point(field_set_pointer:phonedata.UpdateSendFile.ip)
}
inline ::std::string* UpdateSendFile::mutable_ip() {
  
  // @@protoc_insertion_point(field_mutable:phonedata.UpdateSendFile.ip)
  return ip_.MutableNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline ::std::string* UpdateSendFile::release_ip() {
  // @@protoc_insertion_point(field_release:phonedata.UpdateSendFile.ip)
  
  return ip_.ReleaseNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
}
inline void UpdateSendFile::set_allocated_ip(::std::string* ip) {
  if (ip != NULL) {
    
  } else {
    
  }
  ip_.SetAllocatedNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), ip);
  // @@protoc_insertion_point(field_set_allocated:phonedata.UpdateSendFile.ip)
}

// optional .phonedata.UpdateSendFile.PFile fileData = 2;
inline bool UpdateSendFile::has_filedata() const {
  return this != internal_default_instance() && filedata_ != NULL;
}
inline void UpdateSendFile::clear_filedata() {
  if (GetArenaNoVirtual() == NULL && filedata_ != NULL) delete filedata_;
  filedata_ = NULL;
}
inline const ::phonedata::UpdateSendFile_PFile& UpdateSendFile::filedata() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateSendFile.fileData)
  return filedata_ != NULL ? *filedata_
                         : *::phonedata::UpdateSendFile_PFile::internal_default_instance();
}
inline ::phonedata::UpdateSendFile_PFile* UpdateSendFile::mutable_filedata() {
  
  if (filedata_ == NULL) {
    filedata_ = new ::phonedata::UpdateSendFile_PFile;
  }
  // @@protoc_insertion_point(field_mutable:phonedata.UpdateSendFile.fileData)
  return filedata_;
}
inline ::phonedata::UpdateSendFile_PFile* UpdateSendFile::release_filedata() {
  // @@protoc_insertion_point(field_release:phonedata.UpdateSendFile.fileData)
  
  ::phonedata::UpdateSendFile_PFile* temp = filedata_;
  filedata_ = NULL;
  return temp;
}
inline void UpdateSendFile::set_allocated_filedata(::phonedata::UpdateSendFile_PFile* filedata) {
  delete filedata_;
  filedata_ = filedata;
  if (filedata) {
    
  } else {
    
  }
  // @@protoc_insertion_point(field_set_allocated:phonedata.UpdateSendFile.fileData)
}

// optional bool isSendFileFinish = 3;
inline void UpdateSendFile::clear_issendfilefinish() {
  issendfilefinish_ = false;
}
inline bool UpdateSendFile::issendfilefinish() const {
  // @@protoc_insertion_point(field_get:phonedata.UpdateSendFile.isSendFileFinish)
  return issendfilefinish_;
}
inline void UpdateSendFile::set_issendfilefinish(bool value) {
  
  issendfilefinish_ = value;
  // @@protoc_insertion_point(field_set:phonedata.UpdateSendFile.isSendFileFinish)
}

inline const UpdateSendFile* UpdateSendFile::internal_default_instance() {
  return &UpdateSendFile_default_instance_.get();
}
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS
// -------------------------------------------------------------------


// @@protoc_insertion_point(namespace_scope)

}  // namespace phonedata

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_TtPhoneUpdateSendFile_2eproto__INCLUDED