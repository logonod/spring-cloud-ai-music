export default class Singer {
  constructor({city = '', id, name, mid}) {
    this.country = city
    this.singer_id = id
    this.singer_mid = mid
    this.singer_name = name
    this.singer_pic = `http://y.gtimg.cn/music/photo_new/T001R150x150M000${mid}.webp`
  }
}
