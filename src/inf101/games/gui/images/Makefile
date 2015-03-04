
all:	step.png normal.png fast.png faster.png play.png pause.png new.png

%.png:	gui.svg
	inkscape --export-id=$* --export-dpi=90 --export-png=$@ $<
