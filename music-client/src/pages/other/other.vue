<template>
  <div class="other" ref="other">
    <scroll ref="scroll" class="other-content" :data="discList">
      <div>
        <div class="other-list">
          <h1 class="list-title"></h1>
          <ul>
            <li @click="selectItem(index)" v-for="(item, index) in discList" :key="index" class="item">
              <div class="icon">
                <img v-lazy="item.cover" width="60" height="60"/>
              </div>
              <div class="text">
                <h2 class="name">{{item.title}}</h2>
                <p class="desc">{{`原唱：${item.artist}  翻唱：AI${item.ai}`}}</p>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <div v-show="!discList.length" class="no-result-wrapper">
        <no-result title="抱歉，没有更多了"></no-result>
      </div>
    </scroll>
    <router-view></router-view>
  </div>
</template>

<script>
import Slider from '../../base/slider/slider'
import Scroll from '../../base/scroll/scroll'
import NoResult from '../../base/no-result/no-ruselt'
import { playlistMixin } from '../../common/js/mixin'
import { mapMutations, mapActions } from 'vuex'
import { getOther } from '../../api/other'

export default {
  mixins: [playlistMixin],
  data() {
    return {
      discList: [],
    }
  },
  created() {
    this._getOther()
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      vm._getOther()
      next();
    });
  },
  methods: {
    handlePlaylist(playlist) {
      const bottom = playlist.length > 0 ? '60px' : ''
      this.$refs.other.style.bottom = bottom
      this.$refs.scroll.refresh()
    },
    selectItem(index) {
      this.selectPlay({
        list: this.discList,
        index,
      })
    },
    _getOther() {
      getOther().then((res) => {
        this.discList = res
      })
    },
    loadImage() {
      if (!this.checkLoaded) {
        this.$refs.scroll.refresh()
        this.checkLoaded = true
      }
    },
    ...mapMutations({
      setDisc: 'SET_DISC'
    }),
    ...mapActions([
      'selectPlay',
    ])
  },
  components: {
    Slider,
    Scroll,
    NoResult
  }
}
</script>

<style lang="stylus" scoped>
  @import "~common/stylus/variable"

  .other
    position: fixed
    width: 100%
    top: 88px
    bottom: 0
    .other-content
      height: 100%
      overflow: hidden
      .slider-wrapper
        position: relative
        width: 100%
        overflow: hidden
      .other-list
        .list-title
          height: 25px
          line-height: 25px
          text-align: center
          font-size: $font-size-medium
          color: $color-theme
        .item
          display: flex
          box-sizing: border-box
          align-items: center
          padding: 0 20px 20px 20px
          .icon
            flex: 0 0 60px
            width: 60px
            padding-right: 20px
          .text
            display: flex
            flex-direction: column
            justify-content: center
            flex: 1
            line-height: 20px
            overflow: hidden
            font-size: $font-size-medium
            .name
              margin-bottom: 10px
              color: $color-text
            .desc
              color: $color-text-d
        .loading-container
          position: absolute
          width: 100%
          top: 50%
          transform: translateY(-50%)
</style>
