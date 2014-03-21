def dir_walker(dir, files_to_read_predicate, read_file_block, dirs_not_to_traverse=[])
	Dir.foreach(dir) do |dir_child|
		if dir_child != "." && dir_child != ".." && !dirs_not_to_traverse.include?(dir_child)
			dir_child_name = "#{dir}\\#{dir_child}"
			if File.directory?(dir_child_name)
				dir_walker(dir_child_name, files_to_read_predicate, read_file_block)
			else
				if File.file?(dir_child_name) && File.readable?(dir_child_name) && files_to_read_predicate.call(dir_child_name)
					File.open(dir_child_name,"r") do |fin|
						read_file_block.call(fin, dir_child_name)
					end
				end
			end
		end
	end
end