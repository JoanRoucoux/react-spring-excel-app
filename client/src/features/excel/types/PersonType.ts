import AddressType from './AddressType';
import ContactType from './ContactType';
import FullNameType from './FullNameType';

interface PersonType {
  id: number;
  fullName: FullNameType;
  birthdate: string;
  address: AddressType;
  contact: ContactType;
}

export default PersonType;
