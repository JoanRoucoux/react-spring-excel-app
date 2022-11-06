import AddressType from './AddressType';
import ContactType from './ContactType';

interface PersonRowType extends AddressType, ContactType {
  id: number;
  fullName: string;
  birthdate: string;
}

export default PersonRowType;
