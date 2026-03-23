/** @type {import("prettier").Config} */
export default {
  printWidth: 100,
  tabWidth: 2,
  useTabs: false,
  semi: false,
  singleQuote: true,
  trailingComma: 'es5',
  bracketSpacing: true,
  arrowParens: 'always',
  endOfLine: 'lf',
  plugins: [],
  overrides: [
    {
      files: ['*.tsx', '*.ts', '*.jsx', '*.js'],
      options: { parser: 'babel-ts' },
    },
  ],
}
