<template>
    <div class="pending" ref="pending">
      <scroll ref="scroll" class="pending-content" :data="discList">
        <div>
          <div class="pending-list">
            <h1 class="list-title"></h1>
            <ul>
              <li v-for="(item, index) in discList" :key="index" class="item">
                <div class="icon">
                  <img v-lazy="item.cover" width="60" height="60"/>
                </div>
                <div class="text">
                  <h2 class="name">{{item.title}}</h2>
                  <p class="desc">{{`原唱：${item.artist}  翻唱：AI${item.ai}  状态: ${item.status}`}}</p>
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
  import Loading from '../../base/loading/loading'
  import Slider from '../../base/slider/slider'
  import Scroll from '../../base/scroll/scroll'
  import NoResult from '../../base/no-result/no-ruselt'
  import { getPending } from '../../api/pending'
  
  export default {
    data() {
      return {
        pendings: [],
        discList: []
      }
    },
    created() {
      this._getPending()
    },
    beforeRouteEnter(to, from, next) {
      next((vm) => {
        vm._getPending()
        next();
      });
    },
    methods: {
      handlePlaylist(playlist) {
        const bottom = playlist.length > 0 ? '60px' : ''
        this.$refs.pending.style.bottom = bottom
        this.$refs.scroll.refresh()
      },
      _getPending() {
        getPending().then((res) => {
          this.discList = res
        })
      },
      loadImage() {
        if (!this.checkLoaded) {
          this.$refs.scroll.refresh()
          this.checkLoaded = true
        }
      },
    },
    components: {
      Slider,
      Scroll,
      Loading,
      NoResult
    }
  }
  </script>
  
  <style lang="stylus" scoped>
    @import "~common/stylus/variable"
  
    .pending
      position: fixed
      width: 100%
      top: 88px
      bottom: 0
      .pending-content
        height: 100%
        overflow: hidden
        .slider-wrapper
          position: relative
          width: 100%
          overflow: hidden
        .pending-list
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
  