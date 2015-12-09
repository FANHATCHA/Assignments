	.file	"strlen.c"
	.text
.globl countLen
	.type	countLen, @function
countLen:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movq	%rdi, -24(%rbp)
	movl	$0, -4(%rbp)
	jmp	.L2
.L3:
	addl	$1, -4(%rbp)
.L2:
	movq	-24(%rbp), %rax
	movzbl	(%rax), %eax
	testb	%al, %al
	setne	%al
	addq	$1, -24(%rbp)
	testb	%al, %al
	jne	.L3
	movl	-4(%rbp), %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	countLen, .-countLen
	.section	.rodata
.LC0:
	.string	"My dog has no nose"
.LC1:
	.string	"Then how does he smell?"
.LC2:
	.string	"Terrible!"
.LC3:
	.string	"%d : %2d : %s\n"
	.text
.globl main
	.type	main, @function
main:
.LFB1:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	pushq	%rbx
	subq	$56, %rsp
	movq	$.LC0, -64(%rbp)
	movq	$.LC1, -56(%rbp)
	movq	$.LC2, -48(%rbp)
	movq	$0, -40(%rbp)
	movl	$0, -24(%rbp)
	jmp	.L6
	.cfi_offset 3, -24
.L7:
	movl	-24(%rbp), %eax
	cltq
	movq	-64(%rbp,%rax,8), %rax
	movq	%rax, %rdi
	call	countLen
	movl	%eax, -20(%rbp)
	movl	-24(%rbp), %eax
	cltq
	movq	-64(%rbp,%rax,8), %rcx
	movl	$.LC3, %eax
	movl	-20(%rbp), %edx
	movl	-24(%rbp), %ebx
	movl	%ebx, %esi
	movq	%rax, %rdi
	movl	$0, %eax
	call	printf
	addl	$1, -24(%rbp)
.L6:
	movl	-24(%rbp), %eax
	cltq
	movq	-64(%rbp,%rax,8), %rax
	testq	%rax, %rax
	jne	.L7
	movl	$0, %edi
	call	exit
	.cfi_endproc
.LFE1:
	.size	main, .-main
	.ident	"GCC: (GNU) 4.4.7 20120313 (Red Hat 4.4.7-16)"
	.section	.note.GNU-stack,"",@progbits
