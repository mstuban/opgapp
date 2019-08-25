export interface IImage {
  id?: number;
  byteArray?: [];
}

export class Image implements IImage {
  constructor(
    public id?: number,
    public byteArray?: []
  ) {
  }
}
