/* src/main/resources/static/heatmap.js */
document.addEventListener('DOMContentLoaded', () => {
  const container = d3.select('#heatmap');
  let width = container.node().getBoundingClientRect().width;
  let height = container.node().getBoundingClientRect().height;

  const svg = container.append('svg')
    .attr('width', width)
    .attr('height', height)
    .attr('viewBox', [0, 0, width, height])
    .style('font', '10px sans-serif');

  const tooltip = d3.select('body').append('div')
    .attr('class', 'tooltip')
    .style('opacity', 0);

  const loading = container.append('div')
    .attr('id', 'loading')
    .style('position', 'absolute')
    .style('top', '50%')
    .style('left', '50%')
    .style('transform', 'translate(-50%,-50%)')
    .style('color', '#aaa')
    .style('font-size', '14px')
    .text('Loading Top 20 S&P 500 Stocks…');

  fetch('/heatmap/all')
    .then(r => { if (!r.ok) throw new Error(r.status); return r.json(); })
    .then(data => {
      loading.remove();
      if (!Array.isArray(data) || data.length === 0) {
        container.append('div')
          .style('color', 'orange')
          .style('padding', '2rem')
          .text('No data received');
        return;
      }
      renderTreemap(data);
    })
    .catch(err => {
      loading.remove();
      container.append('div')
        .style('color', 'red')
        .style('padding', '2rem')
        .text('Error: ' + err);
      console.error(err);
    });

  function getColor(pct) {
    if (pct === 0) return '#555555'
    if (pct < 0 && pct > -1) return '#524242';           // Grey
    if (pct <= -3) return '#FF0000';                     // Bright red
    if (pct <= -2) return '#A30000';                     // Dark red
    if (pct <= -1) return '#570000';                     // Deep red
    if (pct > 0 && pct <1) return '#5F735F'
    if (pct >= 3) return '#00FF00';                      // Bright green
    if (pct >= 2) return '#00A300';                      // Dark green
    if (pct >= 1) return '#004700';                      // Deep green
    return '#555555';
  }

  function renderTreemap(stockData) {
    const rootData = {
      name: 'Top 30 S&P 500',
      children: stockData.map(s => ({
        name: s.symbol,
        value: s.marketCap || 0,
        pct: s.marketPriceChgPct || 0,
        price: s.price || 0,
        chg: s.marketPriceChg || 0,
        fullName: s.name,
        industry: s.industry,
        ipoDate: s.ipoDate,
        webUrl: s.webUrl,
        shareOutStanding: s.shareOutStanding
      }))
    };

    const treemap = d3.treemap()
      .size([width, height])
      .paddingOuter(3)
      .paddingTop(20)
      .paddingInner(1)
      .round(true);

    const root = treemap(d3.hierarchy(rootData)
      .sum(d => d.value)
      .sort((a, b) => b.value - a.value));

    const node = svg.selectAll('g')
      .data(root.descendants().filter(d => d.depth > 0))
      .join('g')
      .attr('transform', d => `translate(${d.x0},${d.y0})`);

    // Background rectangles
    node.append('rect')
      .attr('class', 'stock-rect')
      .attr('width', d => d.x1 - d.x0)
      .attr('height', d => d.y1 - d.y0)
      .attr('fill', d => getColor(d.data.pct))
      .on('mouseover', (e, d) => {
        const s = d.data;
        tooltip.transition().duration(150).style('opacity', .95);
        tooltip.html(`
          <strong>${s.fullName}</strong><br/>
          Symbol: ${s.name}<br/>
          Industry: ${s.industry}<br/>
          Price: $${s.price.toFixed(2)}<br/>
          Change: ${s.chg >= 0 ? '+' : ''}$${s.chg.toFixed(2)} 
          (${s.pct >= 0 ? '+' : ''}${s.pct.toFixed(2)}%)<br/>
          Market Cap: $${(s.value / 1e9).toFixed(2)}B<br/>
          Shares: ${(s.shareOutStanding / 1e6).toFixed(2)}M<br/>
          IPO: ${s.ipoDate}<br/>
          <a href="${s.webUrl}" target="_blank" style="color:#a0d8ff;">Website</a>
        `)
          .style('left', (e.pageX + 12) + 'px')
          .style('top', (e.pageY - 28) + 'px');
      })
      .on('mousemove', e => tooltip
        .style('left', (e.pageX + 12) + 'px')
        .style('top', (e.pageY - 28) + 'px'))
      .on('mouseout', () => tooltip.transition().duration(400).style('opacity', 0));

// ----------  LABELS (centered, white with shadow) ----------
node.filter(d => (d.x1 - d.x0) > 70 && (d.y1 - d.y0) > 45)
  .append('text')
  .attr('class', 'stock-label')
  .attr('x', d => (d.x0 + d.x1) / 2)          // centre of the cell
  .attr('y', d => (d.y0 + d.y1) / 2)
  .attr('text-anchor', 'middle')
  .attr('dominant-baseline', 'middle')
  .style('pointer-events', 'none')
  .each(function (d) {
    const text = d3.select(this);
    const pct = d.data.pct;
    const sign = pct >= 0 ? '+' : '';
    const pctText = `${sign}${Math.abs(pct).toFixed(2)}%`;

    // ---- two lines: symbol + % ----
    const lines = [
      { txt: d.data.name, size: '13px', weight: 'bold', dy: '-0.6em' },
      { txt: pctText,      size: '10px', weight: 'bold', dy: '1.3em' }
    ];

    const tspans = text.selectAll('tspan')
      .data(lines)
      .join('tspan')
      .attr('dy', (l, i) => i === 0 ? l.dy : l.dy)   // first line offset, second line offset
      .style('font-size', l => l.size)
      .style('font-weight', l => l.weight)
      .text(l => l.txt);

    // ---- hide label if it does not fit ----
    const bbox = this.getBBox();
    const w = d.x1 - d.x0 - 10;   // 5 px margin on each side
    const h = d.y1 - d.y0 - 10;
    if (bbox.width > w || bbox.height > h) {
      text.remove();               // overflow → delete whole <text>
    }
  });

    // Add text shadow via filter
    const defs = svg.append('defs');
    const filter = defs.append('filter')
      .attr('id', 'text-shadow')
      .attr('x', '-50%')
      .attr('y', '-50%')
      .attr('width', '200%')
      .attr('height', '200%');

    filter.append('feDropShadow')
      .attr('dx', 1)
      .attr('dy', 1)
      .attr('stdDeviation', 1.5)
      .attr('flood-color', '#000')
      .attr('flood-opacity', 0.8);
  }

  // Resize handler
  let resizeTimer;
  window.addEventListener('resize', () => {
    clearTimeout(resizeTimer);
    resizeTimer = setTimeout(() => {
      width = container.node().getBoundingClientRect().width;
      height = container.node().getBoundingClientRect().height;
      svg.attr('width', width).attr('height', height).attr('viewBox', [0, 0, width, height]);
      container.selectAll('*').remove();
      fetch('/heatmap/all').then(r => r.json()).then(renderTreemap).catch(console.error);
    }, 250);
  });
});