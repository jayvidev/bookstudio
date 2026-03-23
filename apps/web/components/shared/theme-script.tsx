export function ThemeScript() {
  return (
    <script
      dangerouslySetInnerHTML={{
        __html: `
          (function() {
            try {
              var theme = (localStorage.getItem('theme') || '').trim() || 
                          (matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
              
              document.documentElement.classList.add(theme);
              
              var meta = document.querySelector('meta[name="theme-color"]');
              if (meta) {
                meta.content = theme === 'dark' 
                  ? 'oklch(0.141 0.005 285.823)' 
                  : 'oklch(1 0 0)';
              }
            } catch (e) {}
          })();
        `.trim(),
      }}
    />
  )
}
