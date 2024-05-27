<template>
  <div class="canvas-container">
    <canvas ref="canvas"></canvas>
  </div>
</template>

<script>
export default {
  name: "LoginBackground",
  data() {
    return {
      canvas: null,
      ctx: null,
      stars: [],
      maxStars: 1300,
      hue: 217,
      canvas2: document.createElement('canvas'),
      ctx2: null,
      resizeTimeout: null // Added to debounce resize event
    };
  },
  methods: {
    setCanvasSize() {
      // Get canvas element using ref
      const container = this.$refs.canvas.parentNode;
      const rect = container.getBoundingClientRect();
      this.canvas.width = rect.width;
      this.canvas.height = rect.height;
      this.ctx2 = this.canvas2.getContext('2d');
      let half = this.canvas2.width / 2;
      let gradient2 = this.ctx2.createRadialGradient(half, half, 0, half, half, half);
      gradient2.addColorStop(0.025, '#CCC');
      gradient2.addColorStop(0.1, 'hsl(' + this.hue + ', 61%, 33%)');
      gradient2.addColorStop(0.25, 'hsl(' + this.hue + ', 64%, 6%)');
      gradient2.addColorStop(1, 'transparent');
      this.ctx2.fillStyle = gradient2;
      this.ctx2.beginPath();
      this.ctx2.arc(half, half, half, 0, Math.PI * 2);
      this.ctx2.fill();
    },
    random(min, max) {
      if (arguments.length < 2) {
        max = min;
        min = 0;
      }
      if (min > max) {
        let hold = max;
        max = min;
        min = hold;
      }
      return Math.floor(Math.random() * (max - min + 1)) + min;
    },
    maxOrbit(x, y) {
      let max = Math.max(x, y);
      let diameter = Math.round(Math.sqrt(max * max + max * max));
      return diameter / 2;
    },
    createStar() {
      let w = this.canvas.width;
      let h = this.canvas.height;
      let orbitRadius = this.random(this.maxOrbit(w, h));
      let radius = this.random(60, orbitRadius) / 8;
      let orbitX = w / 2;
      let orbitY = h / 2;
      let timePassed = this.random(0, this.maxStars);
      let speed = this.random(orbitRadius) / 50000;
      let alpha = this.random(2, 10) / 10;
      return {
        orbitRadius,
        radius,
        orbitX,
        orbitY,
        timePassed,
        speed,
        alpha
      };
    },
    drawStar(star) {
      let x = Math.sin(star.timePassed) * star.orbitRadius + star.orbitX;
      let y = Math.cos(star.timePassed) * star.orbitRadius + star.orbitY;
      let twinkle = this.random(10);
      if (twinkle === 1 && star.alpha > 0) {
        star.alpha -= 0.05;
      } else if (twinkle === 2 && star.alpha < 1) {
        star.alpha += 0.05;
      }
      this.ctx.globalAlpha = star.alpha;
      this.ctx.drawImage(this.canvas2, x - star.radius / 2, y - star.radius / 2, star.radius, star.radius);
      star.timePassed += star.speed;
    },
    animation() {
      let w = this.canvas.width;
      let h = this.canvas.height;
      this.ctx.globalCompositeOperation = 'source-over';
      this.ctx.globalAlpha = 0.5;
      this.ctx.fillStyle = 'hsla(' + this.hue + ', 64%, 6%, 2)';
      this.ctx.fillRect(0, 0, w, h);
      this.ctx.globalCompositeOperation = 'lighter';
      for (let i = 0; i < this.stars.length; i++) {
        this.drawStar(this.stars[i]);
      }
      window.requestAnimationFrame(this.animation);
    },
    handleResize() {
      // Debounce the resize event
      clearTimeout(this.resizeTimeout);
      this.resizeTimeout = setTimeout(() => {
        this.setCanvasSize();
        // Recreate stars to adjust their positions
        this.stars = [];
        for (let i = 0; i < this.maxStars; i++) {
          this.stars.push(this.createStar());
        }
      }, 200);
    }
  },
  mounted() {
    // Get canvas element using ref
    this.canvas = this.$refs.canvas;
    this.ctx = this.canvas.getContext('2d');
    this.setCanvasSize();
    window.addEventListener('resize', this.handleResize);
    for (let i = 0; i < this.maxStars; i++) {
      this.stars.push(this.createStar());
    }
    this.animation();
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.handleResize);
  }
};
</script>

<style scoped>
.canvas-container {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: -1;
}

canvas {
  display: block;
  transform-origin: 0 0;
  width: 100%;
  height: 100%;
}
</style>
