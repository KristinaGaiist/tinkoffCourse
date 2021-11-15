export class ArrayUtils {
  static contains<T>(array: T[], item: T) {
    return array.indexOf(item) != -1;
  }
}
