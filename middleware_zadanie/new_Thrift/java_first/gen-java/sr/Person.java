package sr;

import javax.annotation.processing.Generated;

/**
 * Autogenerated by Thrift Compiler (0.14.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@Generated(value = "Autogenerated by Thrift Compiler (0.14.1)", date = "2021-06-03")
public class Person implements org.apache.thrift.TBase<Person, Person._Fields>, java.io.Serializable, Cloneable, Comparable<Person> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("sr.Person");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SURNAME_FIELD_DESC = new org.apache.thrift.protocol.TField("surname", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SKILLS_FIELD_DESC = new org.apache.thrift.protocol.TField("skills", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PersonStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PersonTupleSchemeFactory();

  public int id; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String name; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String surname; // required
  public @org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> skills; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    NAME((short)2, "name"),
    SURNAME((short)3, "surname"),
    SKILLS((short)4, "skills");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // NAME
          return NAME;
        case 3: // SURNAME
          return SURNAME;
        case 4: // SKILLS
          return SKILLS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SURNAME, new org.apache.thrift.meta_data.FieldMetaData("surname", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SKILLS, new org.apache.thrift.meta_data.FieldMetaData("skills", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Person.class, metaDataMap);
  }

  public Person() {
  }

  public Person(
    int id,
    java.lang.String name,
    java.lang.String surname,
    java.util.List<java.lang.String> skills)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.name = name;
    this.surname = surname;
    this.skills = skills;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Person(Person other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetSurname()) {
      this.surname = other.surname;
    }
    if (other.isSetSkills()) {
      java.util.List<java.lang.String> __this__skills = new java.util.ArrayList<java.lang.String>(other.skills);
      this.skills = __this__skills;
    }
  }

  public Person deepCopy() {
    return new Person(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.name = null;
    this.surname = null;
    this.skills = null;
  }

  public int getId() {
    return this.id;
  }

  public Person setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getName() {
    return this.name;
  }

  public Person setName(@org.apache.thrift.annotation.Nullable java.lang.String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getSurname() {
    return this.surname;
  }

  public Person setSurname(@org.apache.thrift.annotation.Nullable java.lang.String surname) {
    this.surname = surname;
    return this;
  }

  public void unsetSurname() {
    this.surname = null;
  }

  /** Returns true if field surname is set (has been assigned a value) and false otherwise */
  public boolean isSetSurname() {
    return this.surname != null;
  }

  public void setSurnameIsSet(boolean value) {
    if (!value) {
      this.surname = null;
    }
  }

  public int getSkillsSize() {
    return (this.skills == null) ? 0 : this.skills.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.String> getSkillsIterator() {
    return (this.skills == null) ? null : this.skills.iterator();
  }

  public void addToSkills(java.lang.String elem) {
    if (this.skills == null) {
      this.skills = new java.util.ArrayList<java.lang.String>();
    }
    this.skills.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.String> getSkills() {
    return this.skills;
  }

  public Person setSkills(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> skills) {
    this.skills = skills;
    return this;
  }

  public void unsetSkills() {
    this.skills = null;
  }

  /** Returns true if field skills is set (has been assigned a value) and false otherwise */
  public boolean isSetSkills() {
    return this.skills != null;
  }

  public void setSkillsIsSet(boolean value) {
    if (!value) {
      this.skills = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((java.lang.Integer)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((java.lang.String)value);
      }
      break;

    case SURNAME:
      if (value == null) {
        unsetSurname();
      } else {
        setSurname((java.lang.String)value);
      }
      break;

    case SKILLS:
      if (value == null) {
        unsetSkills();
      } else {
        setSkills((java.util.List<java.lang.String>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case NAME:
      return getName();

    case SURNAME:
      return getSurname();

    case SKILLS:
      return getSkills();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case NAME:
      return isSetName();
    case SURNAME:
      return isSetSurname();
    case SKILLS:
      return isSetSkills();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof Person)
      return this.equals((Person)that);
    return false;
  }

  public boolean equals(Person that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_surname = true && this.isSetSurname();
    boolean that_present_surname = true && that.isSetSurname();
    if (this_present_surname || that_present_surname) {
      if (!(this_present_surname && that_present_surname))
        return false;
      if (!this.surname.equals(that.surname))
        return false;
    }

    boolean this_present_skills = true && this.isSetSkills();
    boolean that_present_skills = true && that.isSetSkills();
    if (this_present_skills || that_present_skills) {
      if (!(this_present_skills && that_present_skills))
        return false;
      if (!this.skills.equals(that.skills))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + id;

    hashCode = hashCode * 8191 + ((isSetName()) ? 131071 : 524287);
    if (isSetName())
      hashCode = hashCode * 8191 + name.hashCode();

    hashCode = hashCode * 8191 + ((isSetSurname()) ? 131071 : 524287);
    if (isSetSurname())
      hashCode = hashCode * 8191 + surname.hashCode();

    hashCode = hashCode * 8191 + ((isSetSkills()) ? 131071 : 524287);
    if (isSetSkills())
      hashCode = hashCode * 8191 + skills.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(Person other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetId(), other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetName(), other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetSurname(), other.isSetSurname());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSurname()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.surname, other.surname);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetSkills(), other.isSetSkills());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSkills()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.skills, other.skills);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("sr.Person(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("surname:");
    if (this.surname == null) {
      sb.append("null");
    } else {
      sb.append(this.surname);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("skills:");
    if (this.skills == null) {
      sb.append("null");
    } else {
      sb.append(this.skills);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PersonStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PersonStandardScheme getScheme() {
      return new PersonStandardScheme();
    }
  }

  private static class PersonStandardScheme extends org.apache.thrift.scheme.StandardScheme<Person> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Person struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SURNAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.surname = iprot.readString();
              struct.setSurnameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SKILLS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.skills = new java.util.ArrayList<java.lang.String>(_list0.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.skills.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setSkillsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Person struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.surname != null) {
        oprot.writeFieldBegin(SURNAME_FIELD_DESC);
        oprot.writeString(struct.surname);
        oprot.writeFieldEnd();
      }
      if (struct.skills != null) {
        oprot.writeFieldBegin(SKILLS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.skills.size()));
          for (java.lang.String _iter3 : struct.skills)
          {
            oprot.writeString(_iter3);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PersonTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PersonTupleScheme getScheme() {
      return new PersonTupleScheme();
    }
  }

  private static class PersonTupleScheme extends org.apache.thrift.scheme.TupleScheme<Person> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Person struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetName()) {
        optionals.set(1);
      }
      if (struct.isSetSurname()) {
        optionals.set(2);
      }
      if (struct.isSetSkills()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetSurname()) {
        oprot.writeString(struct.surname);
      }
      if (struct.isSetSkills()) {
        {
          oprot.writeI32(struct.skills.size());
          for (java.lang.String _iter4 : struct.skills)
          {
            oprot.writeString(_iter4);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Person struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.surname = iprot.readString();
        struct.setSurnameIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list5 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
          struct.skills = new java.util.ArrayList<java.lang.String>(_list5.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readString();
            struct.skills.add(_elem6);
          }
        }
        struct.setSkillsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

