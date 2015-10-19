**~/.vimrc Configuration File**

```vim
set expandtab
set shiftwidth=2
set tabstop=2
set nu
set autoindent
syntax on
command W w
command Q q
command WQ wq
command Wq wq
```

**Control Mode Commands:**

Repeat insertion n times: `n` `i` `TYPE_WHILE_IN_INSERT_MODE` `ESCAPE`

Repeat last command: `.`

Indent current line: `>>`

Indent n lines, use ‘<’ for unindent, ‘>>’ for two indents, etc.: `>n`

Goto line n: `:n`

Skip n lines: `n` `ENTER`

Replace ‘foo’ with ‘bar’, including ‘%’ searches the entire file: `:%s/foo/bar/g`

Pastes n times: `np`

Undo: `u`

Go to end of line: `fn`+`→` (NOTE: Other arrows are used for start of line, page up and page down)

Peek at the console: `fn`+`shift`+`→` (NOTE: This also works in insert mode, press any key to return to vim)

Return to console, putting vim in background: `ctrl`+`z` (NOTE: Running `fg` in the terminal returns to vim)

Duplicate line: `yy` to copy and `p` to paste. In short: `yyp`, I remember this because it looks like Yuppie!

Print current file (incuding syntax highlighting) : `:hardcopy`
